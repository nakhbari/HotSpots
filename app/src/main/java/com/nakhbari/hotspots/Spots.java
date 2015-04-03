package com.nakhbari.hotspots;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nima on 4/2/2015.
 */
public class Spots {
    private float temperature;
    private LatLng location;
    private String name;

    public Spots(){
        temperature = 0;
        location = null;
        name = "name";
    }

    public void setLocation(LatLng location) {
        this.location = location;
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

    public LatLng getLocation(){
        return this.location;
    }
}
