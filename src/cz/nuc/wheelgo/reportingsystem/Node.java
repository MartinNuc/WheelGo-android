package cz.nuc.wheelgo.reportingsystem;

/**
 * Created with IntelliJ IDEA.
 * User: mist
 * Date: 17.4.13
 * Time: 18:22
 * To change this template use File | Settings | File Templates.
 */
public class Node
{
    private double latitude;
    private double longitude;
    private String description;
    private int bearing=0;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBearing() {
        return bearing;
    }

    public void setBearing(int bearing) {
        this.bearing = bearing;
    }

}
