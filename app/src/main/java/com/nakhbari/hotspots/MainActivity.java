package com.nakhbari.hotspots;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;


public class MainActivity extends Activity implements View.OnClickListener, OnMapReadyCallback, FragmentList.FragmentListCommunicator {

    private static class ViewHolder{
        Button bMap;
        Button bList;
        Button bSearchLocation;
        EditText etLocation;
        TextView tvCurrentTemp;
        ProgressBar pbProgressBar;
    }

    private ViewHolder holder;
    private FragmentManager fm = getFragmentManager();
    private FragmentList fragmentList = new FragmentList();
    private MapFragment fragmentMap = new MapFragment();
    private String lastSearch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            initializeView();

            // initialize Fragment
            fm.beginTransaction().add(R.id.mainContainer, fragmentList).commit();
        }
    }

    public void initializeView(){
        // set up views
        holder = new ViewHolder();
        holder.bMap= (Button) findViewById(R.id.bMap);
        holder.bList= (Button) findViewById(R.id.bList);
        holder.bSearchLocation= (Button) findViewById(R.id.bSearchLocation);
        holder.etLocation= (EditText) findViewById(R.id.etLocation);
        holder.tvCurrentTemp = (TextView) findViewById(R.id.tvCurrentTemp);
        holder.pbProgressBar = (ProgressBar) findViewById(R.id.pbLoadingCities);

        // Initialize onClick listeners
        holder.bSearchLocation.setOnClickListener(this);
        holder.bMap.setOnClickListener(this);
        holder.bList.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bSearchLocation:
                String search = this.holder.etLocation.getText().toString();
                if(!this.lastSearch.equals(search)) {
                    lastSearch = search;
                    JSONCurrentWeatherTask task = new JSONCurrentWeatherTask();
                    task.execute(search);
                }
                break;
            case R.id.bList:
                if(fragmentList != fm.findFragmentById(R.id.mainContainer)){
                    fm.beginTransaction().replace(R.id.mainContainer, fragmentList).commit();
                }
                break;
            case R.id.bMap:
                if(fragmentMap != fm.findFragmentById(R.id.mainContainer)){
                    fm.beginTransaction().replace(R.id.mainContainer, fragmentMap).commit();
                    fragmentMap.getMapAsync(this);
                }
                break;

        }
    }

    private class JSONCurrentWeatherTask extends AsyncTask<String, Void, Spots> {

        @Override
        protected Spots doInBackground(String... params) {
            WeatherHTTPClient client = new WeatherHTTPClient();
            String data = client.getWeatherData(params[0], true);
            Spots spot = null;
            try {
                spot = JSONWeatherParser.getSpot(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return spot;
        }

        @Override
        protected void onPostExecute(Spots spot) {
            super.onPostExecute(spot);
            holder.tvCurrentTemp.setText("Current Temp: " + Helper.FormatTemperatureToString(spot.getTemperature()) + "C");
            JSONCaptialWeatherTask task = new JSONCaptialWeatherTask();
            task.execute(spot.getTemperature());
        }
    }

    private class JSONCaptialWeatherTask extends AsyncTask<Float, Void, ArrayList<Spots>> {
        @Override
        protected ArrayList<Spots> doInBackground(Float... params) {
            float currentTmp = params[0];
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    holder.pbProgressBar.setVisibility(View.VISIBLE);
                }
            });
            WeatherHTTPClient client = new WeatherHTTPClient();
            String ids = getResources().getString(R.string.capitalWeatherIDs);
            String data = client.getWeatherData(ids, false);
            ArrayList<Spots> spots = null;
            try {
                spots = JSONWeatherParser.getSpots(data);
                Spots spot;
                PriorityQueue<Integer> removeList = new PriorityQueue<>(20, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer lhs, Integer rhs) {
                        return rhs - lhs;
                    }
                });

                for(int i = 0; i < spots.size(); i++){
                    spot = spots.get(i);
                    if(spot.getTemperature() > currentTmp) {
                        spot.setImage((new WeatherHTTPClient()).getWeatherImage(spot.getIconID()));
                    }else{
                        removeList.add(i);
                    }

                }

                for(int i : removeList){
                    spots.remove(i);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return spots;
        }

        @Override
        protected void onPostExecute(ArrayList<Spots> spots) {
            super.onPostExecute(spots);
            fragmentList.setSpots(spots);
            holder.pbProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void updateMap() {
        fragmentMap.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ArrayList<Spots> spots = fragmentList.getSpotList();

        for(Spots spot : spots){
            googleMap.addMarker(new MarkerOptions().position(spot.getCoordinates()).title(spot.getName() + " " + Helper.FormatTemperatureToString(spot.getTemperature()) + "C"));//.icon(BitmapDescriptorFactory.fromBitmap(spot.getImage())));
        }
    }

}
