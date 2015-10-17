package com.crowdsocial.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crowdsocial.model.Event;

public class MyEventsListFragment extends EventListFragment {

    public static MyEventsListFragment newInstance() {
        Bundle args = new Bundle();
        MyEventsListFragment fragment = new MyEventsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        addAllEvents(Event.getDummyMyEvents());
        return view;
    }
}
