package cz.nuc.wheelgo.androidclient.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.List;

import cz.nuc.wheelgo.androidclient.service.dto.NavigationParameters;
import cz.nuc.wheelgo.androidclient.service.dto.ProblemDto;
import cz.nuc.wheelgo.androidclient.service.dto.Route;

/**
 * Created by mist on 14.11.13.
 */
public class WheelGoService {

    String url;
    Gson gson = new Gson();
    GetRequest get ;
    PostRequest post ;
    DeleteRequest delete ;

    public WheelGoService(String serverIp) {
        this.url = "http://" + serverIp + "/WheelGoServer/rest/api/";
        this.get = new GetRequest(url);
        this.post = new PostRequest(url);
        this.delete = new DeleteRequest(url);
    }

    public boolean addProblem(ProblemDto problem) throws ConnectException {
        post.setJsonBody(gson.toJson(problem));
        InputStream source = post.sendRequest("problem");
        Reader reader = new InputStreamReader(source);
        return gson.fromJson(reader, Boolean.class);
    }

    public boolean deleteProblem(long problemId) throws ConnectException {
        InputStream source = delete.sendRequest("problem?problemId=" + problemId);
        Reader reader = new InputStreamReader(source);
        return gson.fromJson(reader, Boolean.class);
    }

    public List<ProblemDto> getProblemsAround(double latitude, double longitude, double radius) throws ConnectException
    {
        InputStream source = get.sendRequest("problems?" + "lat=" + latitude + "&long=" + longitude + "&radius=" + radius);
        Reader reader = new InputStreamReader(source);
        Type listType = new TypeToken<List<ProblemDto>>(){}.getType();
        return gson.fromJson(reader, listType);
    }

    public Route navigate(NavigationParameters parameters) throws ConnectException
    {
        post.setJsonBody(gson.toJson(parameters));
        InputStream source = post.sendRequest("navigate");
        Reader reader = new InputStreamReader(source);
        return gson.fromJson(reader, Route.class);
    }

    public Route navigate(double fromLatitude, double fromLongitude, double toLatitude, double toLongitude) throws ConnectException {
        NavigationParameters param = new NavigationParameters();
        param.latitudeFrom = fromLatitude;
        param.longitudeFrom = fromLongitude;
        param.latitudeTo = toLatitude;
        param.longitudeTo = toLongitude;

        return navigate(param);
    }

    public ProblemDto getProblemDetail(Long problemId) throws ConnectException {
        InputStream source = get.sendRequest("problem?problemId=" + problemId);
        Reader reader = new InputStreamReader(source);
        return gson.fromJson(reader, ProblemDto.class);
    }
}
