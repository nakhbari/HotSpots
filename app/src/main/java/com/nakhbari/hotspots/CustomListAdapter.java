package com.nakhbari.hotspots;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nima on 4/2/2015.
 */
public class CustomListAdapter extends ArrayAdapter<Spots> {
    public CustomListAdapter(Context context, int textViewResourceId,
                             ArrayList<Spots> rowEntries) {
        super(context, textViewResourceId, rowEntries);
    }

    private static class ViewHolder{
        TextView tvName;
        TextView tvCountry;
        ImageView ivIcon;
        TextView tvTemp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();

        View view = convertView;

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.row_list_entry, null);

            holder.tvName = (TextView) view.findViewById(R.id.tvEntryName);
            holder.tvCountry = (TextView) view.findViewById(R.id.tvEntryCountry);
            holder.tvTemp = (TextView) view.findViewById(R.id.tvEntryTemp);
            holder.ivIcon = (ImageView) view.findViewById(R.id.ivIcon);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        Spots spot = getItem(position);
        if(holder.tvName != null){
            holder.tvName.setText(spot.getName());
        }
        if(holder.tvCountry != null){

            holder.tvCountry.setText(spot.getCountry());
        }
        if(holder.tvTemp != null){

            holder.tvTemp.setText(Helper.FormatTemperatureToString(spot.getTemperature())+"C");

        }

        if(holder.ivIcon != null && spot.getImage() != null){
            holder.ivIcon.setImageBitmap(spot.getImage());
        }


        return view;
    }
}
