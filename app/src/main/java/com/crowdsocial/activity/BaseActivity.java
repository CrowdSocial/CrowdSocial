package com.crowdsocial.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.crowdsocial.R;
import com.crowdsocial.dialog.LoginRegisterDialogFragment;

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

    public void onAccountClick(MenuItem item) {
        showLoginRegisterDialog();
    }


    private void showLoginRegisterDialog() {
        FragmentManager fm = getSupportFragmentManager();
        LoginRegisterDialogFragment dialog = LoginRegisterDialogFragment.newInstance();
        dialog.show(fm, "login_register_fragment");
    }
}
