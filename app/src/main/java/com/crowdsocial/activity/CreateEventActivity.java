package com.crowdsocial.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdsocial.R;
import com.crowdsocial.fragment.FinalFragment;
import com.crowdsocial.fragment.Step1Fragment;
import com.crowdsocial.model.Event;
import com.crowdsocial.model.Invitee;
import com.crowdsocial.util.BitmapScaler;
import com.crowdsocial.util.ParseErrorHandler;
import com.crowdsocial.util.ParseUserUtil;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CreateEventActivity extends BaseActivity implements
        FinalFragment.OnContactSelectedListener {

    ViewPager viewPager;
    EventCreateStepsPagerAdapter pagerAdapter;

    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private final static int SEND_EMAIL_ACTIVITY_REQUEST_CODE = 1035;
    private final static String EMAIL_URL = "http://crowdsocial.codepath.com";

    private String eventImageFileName = "photo.jpg";
    private String eventImageUrl;
    private Date eventDate;
    private ArrayList<Invitee> invitees;
    private static final String EMAIL_MSG_TYP = "text/html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        invitees = new ArrayList<>();
        pagerAdapter =
                new EventCreateStepsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.hold, R.anim.slide_down);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public void nextStep(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void previousStep(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    @Override
    public void onContactSelected(String email, String name) {
        Invitee invitee = new Invitee();
        if(!TextUtils.isEmpty(email)) {
            invitee.setEmail(email);
        }
        if(!TextUtils.isEmpty(name)) {
            invitee.setName(name);
        }
        invitee.setAccepted(false);
        if(invitees.contains(invitee)) {
            invitees.remove(invitee);
        } else {
            invitees.add(invitee);
        }
    }

    public void setEventDate(View view) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.YEAR, year);

                eventDate = c.getTime();

                TextView tv = (TextView) findViewById(R.id.tvDate);

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
                tv.setText(dateFormat.format(eventDate));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void createEvent(View view) {

        if(!isValidEvent()) {
            Toast.makeText(CreateEventActivity.this,
                    "Please enter valid Event details", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText etEventTitle = (EditText) findViewById(R.id.etEventTitle);
        EditText etDescription = (EditText) findViewById(R.id.etDescription);
        EditText etAddress = (EditText) findViewById(R.id.etAddress);
        EditText etAmount = (EditText) findViewById(R.id.etAmount);

        final Event event = new Event();
        event.setUser(ParseUserUtil.getLoggedInUser());
        event.setParticipationAmount(Integer.valueOf(etAmount.getText().toString()));
        event.setParticipationAmount(Integer.valueOf(etAmount.getText().toString()));
        event.setDescription(etDescription.getText().toString());
        event.setLocation(etAddress.getText().toString());
        event.setTitle(etEventTitle.getText().toString());
        event.setEventDate(eventDate);
        if(eventImageUrl != null) {
            event.setImageUrl(eventImageUrl);
        }

        for(Invitee i : invitees) {
            event.addInvitee(i);
        }

        Invitee.saveAllInBackground(invitees, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    ParseErrorHandler.handleError(e);
                } else {
                    event.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                ParseErrorHandler.handleError(e);
                            } else {
                                if (invitees.size() > 0) {
                                    String eventId = event.getObjectId();
                                    sendEmail(
                                            getString(R.string.invitation_crowdsocial)
                                            , getEmailBody(
                                                    ParseUserUtil.getLoggedInUser().getEmail(),
                                                    event.getTitle(),
                                                    EMAIL_URL + "/event/" + eventId)
                                                    , getInviteeEmails(invitees));
                                }
                            }
                        }
                    });
                }

            }
        });
    }

    private boolean isValidEvent() {
        EditText etEventTitle = (EditText) findViewById(R.id.etEventTitle);
        EditText etDescription = (EditText) findViewById(R.id.etDescription);
        EditText etAddress = (EditText) findViewById(R.id.etAddress);
        EditText etAmount = (EditText) findViewById(R.id.etAmount);

        if(TextUtils.isEmpty(etEventTitle.getText().toString()))
            return false;
        if(TextUtils.isEmpty(etAddress.getText().toString()))
            return false;
        if(TextUtils.isEmpty(etDescription.getText().toString()))
            return false;
        try {
            Integer.parseInt(etAmount.getText().toString());
        } catch (NumberFormatException e) {
            return false;
        }
        if(eventDate == null)
            return false;
        if(invitees.size() == 0)
            return false;
        return true;
    }

    public class EventCreateStepsPagerAdapter extends FragmentStatePagerAdapter {

        public EventCreateStepsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return Step1Fragment.newInstance();
            } else if (position == 1) {
                return FinalFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

    private void sendEmail(String subject, String body, HashSet<String> recipients) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType(EMAIL_MSG_TYP);
        i.putExtra(Intent.EXTRA_EMAIL, recipients.toArray(new String[recipients.size()]));
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(body));
        try {
            startActivityForResult(Intent.createChooser(i, "Send mail..."),
                    SEND_EMAIL_ACTIVITY_REQUEST_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CreateEventActivity.this,
                    R.string.no_email_clients_installed, Toast.LENGTH_SHORT).show();
        }
    }

    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(eventImageFileName)); // set the image file name
        // Start the image capture intent to take photo
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri takenPhotoUri = getPhotoFileUri(eventImageFileName);
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());

                takenImage = BitmapScaler.scaleToFitHeight(takenImage, 400);

                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                takenImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();
                final ParseFile file = new ParseFile(image);

                ImageView ivEvent = (ImageView) findViewById(R.id.ivEvent);
                ivEvent.setImageBitmap(takenImage);

                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            ParseErrorHandler.handleError(e);
                        } else {
                            eventImageUrl = file.getUrl();
                        }
                    }
                });

            } else { // Result was a failure
                Toast.makeText(this, R.string.no_picture_taken, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SEND_EMAIL_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(
                    this, R.string.event_created, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            File mediaStorageDir = new File(
                    Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES), getClass().getName());

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(getClass().getName(), "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    private String getEmailBody(String email, String title, String link) {
        String body = getString(R.string.invitation_template);
        return body.replace("{user_email}", email)
                .replace("{event_title}", title)
                .replace("{link}", link);
    }

    private HashSet<String> getInviteeEmails(ArrayList<Invitee> invitees) {
        HashSet<String> emails = new HashSet<>();
        for (Invitee i : invitees) {
            if(!TextUtils.isEmpty(i.getEmail())) {
                emails.add(i.getEmail());
            }
        }

        return emails;
    }
}
