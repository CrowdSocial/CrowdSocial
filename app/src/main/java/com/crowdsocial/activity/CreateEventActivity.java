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
import android.widget.TextView;

import com.crowdsocial.R;
import com.crowdsocial.fragment.FinalFragment;
import com.crowdsocial.fragment.Step1Fragment;
import com.crowdsocial.fragment.Step2Fragment;
import com.crowdsocial.model.Event;

import java.util.Calendar;

public class CreateEventActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new EventCreateStepsPagerAdapter(getSupportFragmentManager()));
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

    public void addAContact(View view) {

    }

    public void nextToStep2(View view) {
        android.app.FragmentManager fm = CreateEventActivity.this.getFragmentManager();
        Step2Fragment step2Fragment = new Step2Fragment();
        Bundle args = new Bundle();
        Event event = new Event();
//        args.putParcelable("event", event);
        step2Fragment.setArguments(args);
//        step2Fragment.setListener(CreateEventActivity.this);
//        step2Fragment.show(fm, "step2Fragment");
    }

    public void setEventDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"Date Picker");
    }

    public class EventCreateStepsPagerAdapter extends FragmentStatePagerAdapter {

        private final String tabTitles[] = {"Step1", "Step2", "Final"};

        public EventCreateStepsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new Step1Fragment();
            } else if (position == 1) {
                return new Step2Fragment();
            } else if (position == 2) {
                return new FinalFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    /**
     * A simple {@link Fragment} subclass.
     */
    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            //Use the current date as the default date in the date picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors - Here we uses first one
            public DatePickerDialog (Context context, DatePickerDialog.OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth)
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
         */
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
