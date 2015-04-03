package com.nakhbari.hotspots;

import android.app.Activity;
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
    FragmentListCommunicator activityCommunicator;
    private CustomListAdapter adapter;
    private ArrayList<Spots> spotList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new CustomListAdapter(getActivity(), R.layout.row_list_entry, spotList);
        setListAdapter(adapter);
    }

    public void setSpots(ArrayList<Spots> spotList){
        this.spotList.clear();
        this.spotList.addAll(spotList);
        this.activityCommunicator.updateMap();

        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public ArrayList<Spots> getSpotList() {
        return this.spotList;
    }

    /** ----------------------- Activity Interface ----------------- */

    @Override
    public void onAttach(Activity activity) {
        // Attach the interface to the activity
        super.onAttach(activity);
        try {
            activityCommunicator = (FragmentListCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement FragmentListCommunicator");
        }
    }

    public interface FragmentListCommunicator {
        public void updateMap();
    }
}
