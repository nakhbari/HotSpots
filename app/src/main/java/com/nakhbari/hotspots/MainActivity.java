package com.nakhbari.hotspots;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;


public class MainActivity extends Activity implements View.OnClickListener {

    private static class ViewHolder{
        Button bMap;
        Button bList;
        Button bSearchLocation;
        EditText etLocation;
        TextView tvCurrentTemp;
    }

    private ViewHolder holder;
    private FragmentManager fm = getFragmentManager();
    private FragmentList fragmentList = new FragmentList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            // set up views
            holder = new ViewHolder();
            holder.bMap= (Button) findViewById(R.id.bMap);
            holder.bList= (Button) findViewById(R.id.bList);
            holder.bSearchLocation= (Button) findViewById(R.id.bSearchLocation);
            holder.etLocation= (EditText) findViewById(R.id.etLocation);
            holder.tvCurrentTemp = (TextView) findViewById(R.id.tvCurrentTemp);

            // Initialize onClick listeners
            holder.bSearchLocation.setOnClickListener(this);
            // initialize Fragment
            fm.beginTransaction().add(R.id.mainContainer, fragmentList).commit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bSearchLocation:
                JSONWeatherTask task = new JSONWeatherTask();
                task.execute(this.holder.etLocation.getText().toString());
                break;

        }
    }
    private class JSONWeatherTask extends AsyncTask<String, Void, Spots> {

        @Override
        protected Spots doInBackground(String... params) {
            WeatherHTTPClient client = new WeatherHTTPClient();
            String data = client.getWeatherData(params[0]);
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
            holder.tvCurrentTemp.setText("Current Temp: " + Helper.FormatTemperatureToString(spot.getTemperature()) + "degC");
        }
    }
}
