package com.crowdsocial.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.crowdsocial.R;
import com.crowdsocial.fragment.FinalFragment;
import com.crowdsocial.fragment.Step1Fragment;
import com.crowdsocial.fragment.Step2Fragment;
import com.crowdsocial.model.Event;

import java.util.Calendar;

public class CreateEventActivity extends BaseActivity {

    ViewPager viewPager;
    EventCreateStepsPagerAdapter pagerAdapter;

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
        Switch swFree = (Switch) findViewById(R.id.swFree);
        EditText etAmount = (EditText) findViewById(R.id.etAmount);
        ListView lvContacts = (ListView) findViewById(R.id.lvContacts);
        TextView tvDate = (TextView) findViewById(R.id.tvDate);

        Event event = new Event();

        //call parse and save the event;
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

    /**
     * A simple {@link Fragment} subclass.
     */
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
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //Do something with the date chosen by the user
            TextView tv = (TextView) getActivity().findViewById(R.id.tvDate);

            String stringOfDate = day + "/" + month + "/" + year;
            tv.setText(stringOfDate);
        }
    }
}
