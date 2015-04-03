package com.nakhbari.hotspots;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Nima on 4/3/2015.
 */
public class WeatherHTTPClient {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";

    public String getWeatherData(String location){
        HttpURLConnection connection = null;
        InputStream is = null;

        try{
            connection = (HttpURLConnection) (new URL(BASE_URL + location)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            // Read response
            StringBuffer buffer = new StringBuffer();
            is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null){
                buffer.append(line + "\r\n");
            }

            is.close();
            connection.disconnect();
            return buffer.toString();
        }catch (Throwable t){
            t.printStackTrace();
        }finally {
            try{is.close();}catch (Throwable t){}
            try {connection.disconnect();}catch (Throwable t){}
        }

        return null;
    }
}
