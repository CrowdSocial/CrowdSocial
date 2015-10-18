package com.crowdsocial.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.crowdsocial.R;
import com.crowdsocial.util.ParseUtil;

public class ProfileActivity extends BaseActivity {

    private Button btLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btLogout = (Button) findViewById(R.id.btLogout);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUtil.logoutUser();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }
}
