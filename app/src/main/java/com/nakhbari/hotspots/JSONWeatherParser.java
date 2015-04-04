package com.nakhbari.hotspots;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nima on 4/3/2015.
 */
public class JSONWeatherParser {

    public static Spots getSpot(String data) throws JSONException {
        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        return getSpotFromObject(jObj);
    }

    public static Spots getSpotFromObject(JSONObject jObj) throws JSONException {
        Spots spot = new Spots();

        // We start extracting the info
        JSONObject coordObj = getObject("coord", jObj);
        spot.setCoordinates(new LatLng(getFloat("lat", coordObj), getFloat("lon", coordObj)));
        JSONObject sysObj = getObject("sys", jObj);
        spot.setCountry(getString("country", sysObj));

        // We get weather info (This is an array)
        JSONArray jArr = jObj.getJSONArray("weather");

        // We use only the first value
        JSONObject JSONWeather = jArr.getJSONObject(0);
        spot.setIconID(getString("icon", JSONWeather));

        JSONObject mainObj = getObject("main", jObj);
        spot.setTemperature(getFloat("temp", mainObj) - 273.15f);

        spot.setName(getString("name", jObj));

        return spot;
    }

    public static ArrayList<Spots> getSpots(String data) throws JSONException {

        ArrayList<Spots> spots = new ArrayList<>();

        // We create out JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        // We get list of weather info (This is an array)
        int count = getInt("cnt", jObj);
        JSONArray jArr = jObj.getJSONArray("list");

        for (int i = 0; i < count; i++) {
            spots.add(getSpotFromObject(jArr.getJSONObject(i)));
        }

        return spots;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
