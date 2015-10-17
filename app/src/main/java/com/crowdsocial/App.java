package com.crowdsocial;
import android.app.Application;
import com.parse.Parse;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(
                this,
                "v0k2DsclfjHMPQkpQWXwirzg3bvj0SA7e149wR9K",
                "05LAoJKyQjChynLjRlb9AbIZOvH7xTV0B4FyMHln");
    }
}