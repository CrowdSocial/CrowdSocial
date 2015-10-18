package com.crowdsocial.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.crowdsocial.R;
import com.squareup.picasso.Picasso;

public class EventDetailActivity extends BaseActivity {

    private ImageView ivEvent;
    private TextView tvLocation;
    private TextView tvAmount;
    private TextView tvParticipateCount;
    private TextView tvCommittedAmount;
    private TextView tvDateTime;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        ivEvent = (ImageView) findViewById(R.id.ivEvent);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvParticipateCount = (TextView) findViewById(R.id.tvParticipateCount);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvCommittedAmount = (TextView) findViewById(R.id.tvCommittedAmount);

        // ------------- temporarily put sample data ---------------
        Picasso.with(this)
                .load("http://lvs.luxury/wp-content/uploads/2015/05/IMG_1266Porche-event.jpg")
                .into(ivEvent);
        tvLocation.setText("San Francisco");
        tvAmount.setText("$25");
        tvParticipateCount.setText("22");
        tvCommittedAmount.setText("$550");
        tvDescription.setText("Here is a brief list of possible items that could take place during " +
                "your event: welcome, introductions, toasts, " +
                "special dances, announcements, roasts");
        tvDateTime.setText("June 25");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_create) {
            Intent i = new Intent(this, CreateEventActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
