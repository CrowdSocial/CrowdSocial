package com.crowdsocial;

import android.app.Application;

import com.crowdsocial.model.Event;
import com.crowdsocial.model.Invitee;
import com.parse.Parse;
import com.parse.ParseObject;

public class App extends Application {

    private static final String APPLICATION_ID = "v0k2DsclfjHMPQkpQWXwirzg3bvj0SA7e149wR9K";
    private static final String CLIENT_KEY = "05LAoJKyQjChynLjRlb9AbIZOvH7xTV0B4FyMHln";

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(getApplicationContext());

        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(Invitee.class);

        Parse.initialize(
                this,
                APPLICATION_ID, CLIENT_KEY);
    }
}