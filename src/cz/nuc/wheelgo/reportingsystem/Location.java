package cz.nuc.wheelgo.reportingsystem;

/**
 * Created with IntelliJ IDEA.
 * User: mist
 * Date: 15.4.13
 * Time: 3:30
 * To change this template use File | Settings | File Templates.
 */
public class Location
{

    protected double latitude;
    protected double longitude;

    /**
     * Gets the value of the latitude property.
     *
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     *
     */
    public void setLatitude(double value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     *
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     *
     */
    public void setLongitude(double value) {
        this.longitude = value;
    }

}

