package com.crowdsocial.activity;

import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdsocial.R;
import com.crowdsocial.dialog.InviteeListDialogFragment;
import com.crowdsocial.model.Event;
import com.crowdsocial.model.Invitee;
import com.crowdsocial.util.ParseErrorHandler;
import com.crowdsocial.util.ParseUserUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventDetailActivity extends BaseActivity implements OnMapReadyCallback {

    private ImageView ivEvent;
    private TextView tvAmount;
    private TextView tvParticipateCount;
    private TextView tvCommittedAmount;
    private TextView tvDateTime;
    private TextView tvDescription;
    private TextView tvInviteeCount;
    private TextView tvVenue;
    private Button btParticipate;
    private TextView tvParticipating;
    private TextView tvInvited;
    private String eventAddress;

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
        tvVenue = (TextView) findViewById(R.id.tvVenue);
        btParticipate = (Button) findViewById(R.id.btParticipate);
        tvParticipating = (TextView) findViewById(R.id.tvParticipating);
        tvInvited = (TextView) findViewById(R.id.tvInvited);

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
                    initilizeMap(event.getLocation());
                    event.getInviteesRelation().getQuery().findInBackground(new FindCallback<Invitee>() {
                        public void done(final List<Invitee> results, ParseException e) {
                            if (e != null) {
                                ParseErrorHandler.handleError(e);
                            } else {
                                try {
                                    if (results.size() > 0 &&
                                            event.getUser().fetchIfNeeded().getEmail().equals(ParseUserUtil.getLoggedInUser().getEmail())) {
                                        tvInvited.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showInviteeDialog(results);
                                            }
                                        });
                                    }
                                } catch (ParseException ex) {
                                    ParseErrorHandler.handleError(ex);
                                }
                                tvInviteeCount.setText(String.valueOf(results.size()));
                                final ArrayList<Invitee> acceptedInvitees = getAcceptedInvitees(results);
                                try {
                                    if (acceptedInvitees.size() > 0 &&
                                            event.getUser().fetchIfNeeded().getEmail().equals(ParseUserUtil.getLoggedInUser().getEmail())) {
                                        tvParticipating.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                showInviteeDialog(acceptedInvitees);
                                            }
                                        });
                                    }
                                } catch (ParseException ex) {
                                    ParseErrorHandler.handleError(ex);
                                }
                                //event organizer + accepted invitees
                                tvParticipateCount.setText(String.valueOf(1 + acceptedInvitees.size()));

                                //event organizers contribution + contribution from accepted invitees
                                int amount = (acceptedInvitees.size() + 1) * event.getParticipationAmount();

                                tvCommittedAmount.setText("$" + String.valueOf(amount));

                                final Invitee invitee = getInvitedUser(results, ParseUserUtil.getLoggedInUser().getEmail());
                                try {
                                    if (event.getUser().fetchIfNeeded().getEmail().equals(ParseUserUtil.getLoggedInUser().getEmail())) {
                                        btParticipate.setEnabled(false);
                                    } else if (invitee != null && !invitee.hasAccepted()) {
                                        btParticipate.setEnabled(true);
                                        btParticipate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                invitee.setAccepted(true);
                                                invitee.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if (e != null) {
                                                            ParseErrorHandler.handleError(e);
                                                        } else {
                                                            btParticipate.setEnabled(false);

                                                            //event organizer + accepted invitees + current invitee
                                                            tvParticipateCount.setText(String.valueOf(1 + 1 + acceptedInvitees.size()));

                                                            //event organizers contribution + contribution from accepted invitees + current invitees contribution
                                                            int amount = (acceptedInvitees.size() + 1 + 1) * event.getParticipationAmount();

                                                            tvCommittedAmount.setText("$" + String.valueOf(amount));
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    } else {
                                        btParticipate.setEnabled(false);
                                    }
                                } catch (ParseException ex) {
                                    ParseErrorHandler.handleError(ex);
                                }
                            }
                        }
                    });
                    Picasso.with(EventDetailActivity.this)
                            .load(event.getImageUrl())
                            .into(ivEvent);
                    tvDescription.setText(event.getDescription());
                    tvVenue.setText(event.getLocation());
                    tvAmount.setText("$" + String.valueOf(event.getParticipationAmount()));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
                    tvDateTime.setText(dateFormat.format(event.getEventDate()));
                }
            }
        });
    }

    private void initilizeMap(String address) {
        if(address != null) {
            try {
                eventAddress = address;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            } catch (Exception e) {
                Log.e(getClass().getName(), "Unable to load Map");
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = getLocationFromAddress(eventAddress);
        map.addMarker(new MarkerOptions().position(latLng).title(eventAddress));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    private void showInviteeDialog(List<Invitee> invitees) {
        FragmentManager fm = getSupportFragmentManager();
        InviteeListDialogFragment dialogFragment = InviteeListDialogFragment.newInstance(invitees);
        dialogFragment.show(fm, "fragment_invitee_list");
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

    private Invitee getInvitedUser(List<Invitee> invitees, String email) {
        for (Invitee invitee : invitees) {
            if (email.equals(invitee.getEmail())) {
                return invitee;
            }
        }
        return null;
    }


    public LatLng getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {
            Log.e(getClass().getName(), "Unable to load Map");
        }
        return p1;
    }
}
