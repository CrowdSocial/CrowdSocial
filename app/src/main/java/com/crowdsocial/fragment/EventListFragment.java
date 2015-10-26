package com.crowdsocial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.crowdsocial.R;
import com.crowdsocial.activity.EventDetailActivity;
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

        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), EventDetailActivity.class);
                i.putExtra("eventId", aEvents.getItem(position).getObjectId());
                i.putExtra("eventTitle", aEvents.getItem(position).getTitle());
                startActivity(i);
            }
        });
        return view;
    }

    public void addAllEvents(List<Event> events) {
        aEvents.addAll(events);
    }

    public void removeAllEvents() {
        aEvents.clear();
    }
}