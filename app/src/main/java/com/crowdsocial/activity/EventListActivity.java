package com.crowdsocial.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.crowdsocial.R;
import com.crowdsocial.fragment.EventListFragmentPagerAdapter;
import com.crowdsocial.util.ParseUserUtil;

import uk.co.chrisjenx.calligraphy.CalligraphyTypefaceSpan;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class EventListActivity extends BaseActivity {

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        if(!ParseUserUtil.isUserLoggedIn()) {
            startLoginActivity();
            return;
        }


        SpannableStringBuilder sBuilder = new SpannableStringBuilder();
        sBuilder.append(getString(R.string.app_name));
        CalligraphyTypefaceSpan typefaceSpan =
                new CalligraphyTypefaceSpan(
                        TypefaceUtils.load(getAssets(), "fonts/Lobster_1.3.otf"));
        sBuilder.setSpan(typefaceSpan, 0, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sBuilder.setSpan(new AbsoluteSizeSpan(70), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        getSupportActionBar().setTitle(sBuilder);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new EventListFragmentPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateEventClick(null);
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

}
