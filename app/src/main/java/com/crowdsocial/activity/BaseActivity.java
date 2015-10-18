package com.crowdsocial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.crowdsocial.R;
import com.crowdsocial.dialog.LoginRegisterDialogFragment;
import com.crowdsocial.util.ParseUtil;

public class BaseActivity extends AppCompatActivity {

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
        if(ParseUtil.isUserLoggedIn()) {
            Intent i = new Intent(this, ProfileActivity.class);
            startActivityForResult(i, 1);
        } else {
            showLoginRegisterDialog();
        }
    }


    private void showLoginRegisterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        LoginRegisterDialogFragment dialog = LoginRegisterDialogFragment.newInstance();
        dialog.show(fm, "login_register_fragment");
    }
}
