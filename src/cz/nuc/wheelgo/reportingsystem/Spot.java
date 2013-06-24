package cz.nuc.wheelgo.reportingsystem;

import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Spot implements Serializable {

    protected LatLng coordinates = null;

    public LatLng getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    protected Bitmap photo = null;

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    protected String describtion = null;

    public String getDescription() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    protected int id;

    public int getId() {
        return id;
    }

    protected String name = null;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SpotType getType() {
        return type;
    }


    public void setType(SpotType type) {
        this.type = type;
    }

    public SpotType type;

    public Spot() {
        this.describtion = "";
        this.id = -1;
        this.name = "";
        this.photo = null;
    }

    public Spot(int id) {
        this.id = id;
        this.describtion = "";
        this.name = "";
        this.photo = null;
    }

}
