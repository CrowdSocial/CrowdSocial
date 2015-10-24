package com.crowdsocial.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdsocial.R;
import com.crowdsocial.fragment.FinalFragment;
import com.crowdsocial.fragment.Step1Fragment;
import com.crowdsocial.fragment.Step2Fragment;
import com.crowdsocial.model.Event;
import com.crowdsocial.model.Invitee;
import com.crowdsocial.util.ParseErrorHandler;
import com.crowdsocial.util.ParseUserUtil;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CreateEventActivity extends BaseActivity {

    ViewPager viewPager;
    EventCreateStepsPagerAdapter pagerAdapter;

    private final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    private final static int SEND_EMAIL_ACTIVITY_REQUEST_CODE = 1035;
    private final static String EMAIL_URL = "http://crowdsocial.codepath.com";

    private String eventImageFileName = "photo.jpg";
    private String eventImageUrl;
    private static final String EMAIL_MSG_TYP = "text/html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        pagerAdapter =
                new EventCreateStepsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
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

    public void setEventDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Date Picker");
    }

    public void createEvent(View view) {

        Spinner spTheme = (Spinner) findViewById(R.id.spTheme);
        EditText etEventTitle = (EditText) findViewById(R.id.etEventTitle);
        EditText etDescription = (EditText) findViewById(R.id.etDescription);
        EditText etAddress = (EditText) findViewById(R.id.etAddress);
        EditText etAmount = (EditText) findViewById(R.id.etAmount);
        ListView lvContacts = (ListView) findViewById(R.id.lvContacts);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        final HashSet<String> inviteeEmails = getEmailsFromListView(lvContacts);

        final Event event = new Event();
        event.setUser(ParseUserUtil.getLoggedInUser());
        event.setParticipationAmount(Integer.valueOf(etAmount.getText().toString()));
        event.setParticipationAmount(Integer.valueOf(etAmount.getText().toString()));
        event.setDescription(etDescription.getText().toString());
        event.setLocation(etAddress.getText().toString());
        event.setTheme(spTheme.getSelectedItem().toString());
        event.setTitle(etEventTitle.getText().toString());
        event.setEventDate(new Date(tvDate.getText().toString()));
        if(eventImageUrl != null) {
            event.setImageUrl(eventImageUrl);
        }

        ArrayList<Invitee> invitees = new ArrayList<>();
        for(String email: inviteeEmails) {
            Invitee invitee = new Invitee();
            invitee.setAccepted(false);
            invitee.setEmail(email);
            invitees.add(invitee);
            event.addInvitee(invitee);
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
                                if (inviteeEmails.size() > 0) {
                                    String eventId = event.getObjectId();
                                    sendEmail(
                                            getString(R.string.invitation_crowdsocial)
                                            , getEmailBody(
                                                    ParseUserUtil.getLoggedInUser().getEmail(),
                                                    event.getTitle(),
                                                    event.getImageUrl(),
                                                    //todo change this to a link to the event
                                                    EMAIL_URL + "/event/" + eventId)
                                                    , inviteeEmails);
                                }
                            }
                        }
                    });
                }

            }
        });
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
                return Step2Fragment.newInstance();
            } else if (position == 2) {
                return FinalFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

    public static class DatePickerFragment
            extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //Use the current date as the default date in the date picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //Create a new DatePickerDialog instance and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //Do something with the date chosen by the user
            TextView tv = (TextView) getActivity().findViewById(R.id.tvDate);
            String stringOfDate = day + "/" + month + "/" + year;
            tv.setText(stringOfDate);
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
                    "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private HashSet<String> getEmailsFromListView(ListView lvContacts) {
        HashSet<String> inviteeEmails = new HashSet<>();
        for(int i = 0; i < lvContacts.getAdapter().getCount(); i++) {
            CheckBox cbContact = (CheckBox)lvContacts.getChildAt(i).findViewById(R.id.cbContact);
            if(cbContact.isChecked()) {
                TextView tvEmail = (TextView) lvContacts.getChildAt(i).findViewById(R.id.tvEmail);
                String email = tvEmail.getText().toString();
                if(!TextUtils.isEmpty(email)) {
                    inviteeEmails.add(email);
                }
            }
        }

        return inviteeEmails;
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

                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                takenImage.compress(Bitmap.CompressFormat.PNG, 50, stream);
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
            if(resultCode == RESULT_OK) {
                Toast.makeText(
                        this, R.string.event_created, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(
                        this, R.string.no_invitations_sent, Toast.LENGTH_SHORT).show();
            }
            finish();
            Intent i = new Intent(this, EventListActivity.class);
            startActivity(i);
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

    private String getEmailBody(String email, String title, String imageUrl, String link) {
        String body = getString(R.string.invitation_template);
        return body.replace("{user_email}", email)
                .replace("{event_title}", title)
                .replace("{link}", link);
    }
}
