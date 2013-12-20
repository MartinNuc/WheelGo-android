package cz.nuc.wheelgo.androidclient.service;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

/**
 * Created by mist on 11.10.13.
 */
public class GetRequest {

    String url;

    public GetRequest(String url) {
        this.url = url;
    }

    public InputStream sendRequest(String path) throws ConnectException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url + path);
        getRequest.setHeader("Content-Type", "application/json; charset=utf-8");

        try {

            HttpResponse getResponse = client.execute(getRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_NO_CONTENT)
            {
                return null;
            }
            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(),
                        "Error " + statusCode + " for URL " + url);
                throw new ConnectException("Chyba spojení se serverem. Status: " + statusCode + " - " + url + path);
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();

        } catch (IOException e) {
            getRequest.abort();
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }

        return null;
    }
}