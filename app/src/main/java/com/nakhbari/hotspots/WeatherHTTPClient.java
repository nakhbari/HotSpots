package com.nakhbari.hotspots;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.PriorityQueue;

/**
 * Created by Nima on 4/3/2015.
 */
public class WeatherHTTPClient {
    private static String NAME_BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String ID_BASE_URL = "http://api.openweathermap.org/data/2.5/group?id=";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private static String IMG_TYPE = ".png";

    public String getWeatherData(String location, boolean isByName){
        if(location != null && location != "") {
            HttpURLConnection connection = null;
            InputStream is = null;

            try {
                String url = (isByName)? NAME_BASE_URL + location : ID_BASE_URL + location;
                connection = (HttpURLConnection) (new URL(url)).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();

                // Read response
                StringBuffer buffer = new StringBuffer();
                is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = br.readLine()) != null) {
                    buffer.append(line + "\r\n");
                }

                is.close();
                connection.disconnect();
                return buffer.toString();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (Throwable t) {
                }
                try {
                    connection.disconnect();
                } catch (Throwable t) {
                }
            }
        }
        return null;
    }

    public Bitmap getWeatherImage(String iconID){
        if(iconID != null && iconID != ""){
            HttpURLConnection connection = null;
            InputStream is = null;

            try{
                connection = (HttpURLConnection) (new URL(IMG_URL + iconID + IMG_TYPE)).openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.connect();

                //read response
                is = connection.getInputStream();
                return BitmapFactory.decodeStream(is);

            }catch (Throwable t) {
                t.printStackTrace();
            }finally {
                try { is.close(); } catch(Throwable t) {}
                try { connection.disconnect(); } catch(Throwable t) {}
            }
        }

        return null;
    }
}
