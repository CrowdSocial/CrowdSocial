package com.crowdsocial.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.crowdsocial.R;
import com.crowdsocial.adapter.EventArrayAdapter;
import com.crowdsocial.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventListFragment extends Fragment {

    private ArrayList<Event> events = new ArrayList<>();
    private EventArrayAdapter aEvents;
    private ListView lvEvents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aEvents = new EventArrayAdapter(this.getContext(), events);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        lvEvents = (ListView) view.findViewById(R.id.lvEvents);
        lvEvents.setAdapter(aEvents);
        return view;
    }

    public void insertEvent(Event event, int pos) {
        aEvents.insert(event, pos);
    }

    public void addAllEvents(List<Event> events) {
        aEvents.addAll(events);
    }
}