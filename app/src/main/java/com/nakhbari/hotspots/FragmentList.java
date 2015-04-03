package com.nakhbari.hotspots;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Nima on 4/2/2015.
 */
public class FragmentList extends ListFragment {
    private CustomListAdapter adapter;
    private ArrayList<Spots> spotList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        spotList.add((new Spots()));
        spotList.add((new Spots()));
        spotList.add((new Spots()));

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new CustomListAdapter(getActivity(), R.layout.row_list_entry, spotList);
        setListAdapter(adapter);
    }
}
