package com.crowdsocial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crowdsocial.R;
import com.crowdsocial.util.GravatarUtil;
import com.crowdsocial.util.ParseUserUtil;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends BaseActivity {

    private Button btLogout;
    private ImageView ivProfile;
    private TextView tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btLogout = (Button) findViewById(R.id.btLogout);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUserUtil.logoutUser();
                Intent i = new Intent(ProfileActivity.this, EventListActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });

        String email = getIntent().getStringExtra("email");
        Picasso.with(this).load(GravatarUtil.getGravatarUrl(email)).into(ivProfile);
        getSupportActionBar().setTitle(email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
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
