package com.crowdsocial.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdsocial.R;
import com.crowdsocial.model.Event;
import com.crowdsocial.model.Invitee;
import com.crowdsocial.util.ParseErrorHandler;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EventDetailActivity extends BaseActivity {

    private ImageView ivEvent;
    private TextView tvAmount;
    private TextView tvParticipateCount;
    private TextView tvCommittedAmount;
    private TextView tvDateTime;
    private TextView tvDescription;
    private TextView tvInviteeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        ivEvent = (ImageView) findViewById(R.id.ivEvent);
        tvAmount = (TextView) findViewById(R.id.tvAmount);
        tvParticipateCount = (TextView) findViewById(R.id.tvParticipateCount);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvCommittedAmount = (TextView) findViewById(R.id.tvCommittedAmount);
        tvInviteeCount = (TextView) findViewById(R.id.tvInviteeCount);

        String eventId = getIntent().getStringExtra("eventId");

        //intent is fired from invitee clicking the event link
        if(eventId == null) {
            Uri data = getIntent().getData();
            List<String> params = data.getPathSegments();
            if(params.size() > 0) {
                eventId = params.get(1);
            } else {
                Toast.makeText(this, R.string.not_able_to_load_event, Toast.LENGTH_SHORT).show();
            }
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        query.getInBackground(eventId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e != null) {
                    ParseErrorHandler.handleError(e);
                } else {
                    final Event event = (Event) object;
                    event.getInviteesRelation().getQuery().findInBackground(new FindCallback<Invitee>() {
                        public void done(List<Invitee> results, ParseException e) {
                            if (e != null) {
                                ParseErrorHandler.handleError(e);
                            } else {
                                tvInviteeCount.setText(String.valueOf(results.size()));
                                ArrayList<Invitee> acceptedInvitees = getAcceptedInvitees(results);
                                tvParticipateCount.setText(String.valueOf(acceptedInvitees.size()));
                                tvCommittedAmount.setText("$" + String.valueOf(acceptedInvitees.size() * event.getParticipationAmount()));
                            }
                        }
                    });
                    Picasso.with(EventDetailActivity.this)
                            .load(event.getImageUrl())
                            .into(ivEvent);
                    tvDescription.setText(event.getDescription());
                    tvAmount.setText("$" + String.valueOf(event.getParticipationAmount()));
                    tvDateTime.setText("June 25");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Invitee> getAcceptedInvitees(List<Invitee> invitees) {
        ArrayList<Invitee> acceptedInvitees = new ArrayList<>();

        for (Invitee invitee : invitees) {
            if (invitee.hasAccepted()) {
                acceptedInvitees.add(invitee);
            }
        }
        return acceptedInvitees;
    }
}
