package com.nakhbari.hotspots;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Nima on 4/3/2015.
 */
public class JSONWeatherParser {

    public static Spots getSpot(String data) throws JSONException{
        Spots spot = new Spots();

            JSONObject jObj = new JSONObject(data);

            JSONObject loc = getObject("coord", jObj);
            spot.setLocation(new LatLng(getFloat("lat", loc), getFloat("lon", loc)));
            spot.setTemperature(getFloat("temp",getObject("main", jObj)) - 273.15f);
            spot.setName(getString("name", jObj));
        return spot;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
