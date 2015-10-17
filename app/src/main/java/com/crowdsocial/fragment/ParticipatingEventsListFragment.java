package com.crowdsocial.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crowdsocial.model.Event;

public class ParticipatingEventsListFragment extends EventListFragment {

    public static ParticipatingEventsListFragment newInstance() {
        Bundle args = new Bundle();
        ParticipatingEventsListFragment fragment = new ParticipatingEventsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        addAllEvents(Event.getDummyParticipatingEvents());
        return view;
    }
}
