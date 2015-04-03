package com.nakhbari.hotspots;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nima on 4/2/2015.
 */
public class Spots {
    private float temperature;
    private LatLng coordinates;
    private String name;
    private String country;
    private String iconID;
    private Bitmap image;

    public Spots(){
        temperature = 0;
        coordinates = null;
        name = "name";
        country = "country";
        iconID = "";
    }

    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
    public float getTemperature(){
        return this.temperature;
    }

    public String getName(){
        return this.name;
    }

    public LatLng getCoordinates(){
        return this.coordinates;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getCountry(){
        return this.country;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setIconID(String iconID) {
        this.iconID = iconID;
    }

    public String getIconID() {
        return iconID;
    }
}
