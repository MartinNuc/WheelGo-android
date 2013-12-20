package cz.nuc.wheelgo.androidclient.service.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mist
 * Date: 14.11.13
 * Time: 17:23
 */
public class NavigationParameters {

    public double latitudeFrom;
    public double longitudeFrom;
    public double latitudeTo;
    public double longitudeTo;
    public Integer maxIncline;

    public List<ProblemDto> problemsToAvoid = new LinkedList<ProblemDto>();

}
