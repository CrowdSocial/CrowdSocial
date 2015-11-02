package com.crowdsocial.fragment;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.crowdsocial.model.Event;
import com.crowdsocial.model.Invitee;
import com.crowdsocial.util.ParseErrorHandler;
import com.crowdsocial.util.ParseUserUtil;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class ParticipatingEventsListFragment extends EventListFragment {

    public static ParticipatingEventsListFragment newInstance() {
        Bundle args = new Bundle();
        ParticipatingEventsListFragment fragment = new ParticipatingEventsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        removeAllEvents();
        if(ParseUserUtil.isUserLoggedIn()) {
            populateParticipatingEvents();
        }
    }

    private void populateParticipatingEvents() {
        ParseQuery<Invitee> query = ParseQuery.getQuery(Invitee.class);
        query.whereEqualTo("accepted", true);
        query.whereEqualTo("email", ParseUserUtil.getLoggedInUser().getEmail());
        pbLoading.setVisibility(ProgressBar.INVISIBLE);
        query.findInBackground(new FindCallback<Invitee>() {
            @Override
            public void done(List<Invitee> objects, ParseException e) {
                pbLoading.setVisibility(ProgressBar.INVISIBLE);
                if (e != null) {
                    ParseErrorHandler.handleError(e);
                } else {
                    for (Invitee i : objects) {
                        ParseQuery<Event> query = ParseQuery.getQuery(Event.class);
                        query.whereEqualTo("invitees", i);
                        query.orderByAscending("eventDate");
                        query.findInBackground(new FindCallback<Event>() {
                            @Override
                            public void done(List<Event> objects, ParseException e) {
                                if (e != null) {
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
            }
        });
    }
}
