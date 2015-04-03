package com.nakhbari.hotspots;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nima on 4/2/2015.
 */
public class CustomListAdapter extends ArrayAdapter<Spots> {
    private ArrayList<Spots> entries;

    public CustomListAdapter(Context context, int textViewResourceId,
                             ArrayList<Spots> rowEntries) {
        super(context, textViewResourceId, rowEntries);
        this.entries = rowEntries;
    }

    private static class ViewHolder{
        TextView tvName;
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

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.tvName.setText(this.entries.get(position).getName());

        return view;
    }
}
