package com.crowdsocial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.crowdsocial.LoginActivity;
import com.crowdsocial.R;
import com.crowdsocial.util.ParseUserUtil;
import com.parse.ParseUser;

public class BaseActivity extends AppCompatActivity {

    public static final int LOGIN_REQUEST_CODE = 12232;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_list, menu);
        return true;
    }

    public void onProfileClick(MenuItem item) {
        if(ParseUserUtil.isUserLoggedIn()) {
            Intent i = new Intent(this, ProfileActivity.class);
            ParseUser parseUser = ParseUserUtil.getLoggedInUser();
            i.putExtra("email", parseUser.getEmail());
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
        } else {
            startLoginActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == LOGIN_REQUEST_CODE) {
            reloadActivity();
        }
    }

    //force activity refresh on login to retrieve user events
    private void reloadActivity() {
        finish();
        startActivity(getIntent());
    }

    public void onCreateEventClick(MenuItem item) {
        if(ParseUserUtil.isUserLoggedIn()) {
            Intent i = new Intent(this, CreateEventActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_up, R.anim.hold);
        } else {
            startLoginActivity();
        }
    }

    protected void startLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivityForResult(i, LOGIN_REQUEST_CODE);
    }
}
