package cz.nuc.wheelgo.androidclient.service;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;

/**
 * Created by mist on 15.12.13.
 */
public class DeleteRequest {
    String url;

    public DeleteRequest(String url) {
        this.url = url;
    }

    public InputStream sendRequest(String path) throws ConnectException {
        DefaultHttpClient client = new DefaultHttpClient();
        final HttpParams httpParameters = client.getParams();

        HttpConnectionParams.setConnectionTimeout(httpParameters, 180 * 1000);
        HttpConnectionParams.setSoTimeout        (httpParameters, 180 * 1000);

        HttpDelete deleteRequest = new HttpDelete(url + path);
        try {
            deleteRequest.setHeader("Content-Type", "application/json; charset=utf-8");

            HttpResponse getResponse = client.execute(deleteRequest);
            final int statusCode = getResponse.getStatusLine().getStatusCode();

            if (statusCode == HttpStatus.SC_NO_CONTENT)
            {
                return null;
            }

            if (statusCode != HttpStatus.SC_OK) {
                Log.w(getClass().getSimpleName(),
                        "Error " + statusCode + " for URL " + url);
                throw new ConnectException("Chyba spojení se serverem." + statusCode + " - " + url + path);
            }

            HttpEntity getResponseEntity = getResponse.getEntity();
            return getResponseEntity.getContent();

        }
        catch (IOException e) {
            deleteRequest.abort();
            Log.w(getClass().getSimpleName(), "Error for URL " + url, e);
        }


        return null;
    }
}
