package com.crowdsocial.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crowdsocial.model.Event;
import com.crowdsocial.util.ParseErrorHandler;
import com.crowdsocial.util.ParseUserUtil;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

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

        if(ParseUserUtil.isUserLoggedIn()) {
            populateUserEvents();
        }
        return view;
    }

    private void populateUserEvents() {
        // Find all events by the current user
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.whereEqualTo("user", ParseUserUtil.getLoggedInUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e != null) {
                    e.printStackTrace();
                    ParseErrorHandler.handleError(e);
                } else {
                    if (objects.size() > 0) {
                        addAllEvents((List<Event>) (List<?>) objects);
                    }
                }
            }
        });
    }
}
