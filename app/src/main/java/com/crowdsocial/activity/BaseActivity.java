package com.crowdsocial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.crowdsocial.R;
import com.crowdsocial.dialog.LoginRegisterDialogFragment;
import com.crowdsocial.util.ParseUserUtil;
import com.parse.ParseUser;

public class BaseActivity extends AppCompatActivity implements
        LoginRegisterDialogFragment.LoginRegisterDialogListener {

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
            showLoginRegisterDialog();
        }
    }

    private void showLoginRegisterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        LoginRegisterDialogFragment dialog = LoginRegisterDialogFragment.newInstance();
        dialog.show(fm, "login_register_fragment");
    }

    public void onFinishLoginRegisterDialog() {
        reloadActivity();
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
            showLoginRegisterDialog();
        }
    }
}
