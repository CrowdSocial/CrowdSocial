package com.crowdsocial.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crowdsocial.R;
import com.crowdsocial.activity.EventDetailActivity;
import com.crowdsocial.adapter.EventArrayAdapter;
import com.crowdsocial.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventListFragment extends Fragment {

    private ArrayList<Event> events = new ArrayList<>();
    private EventArrayAdapter aEvents;
    private RecyclerView rvEvents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aEvents = new EventArrayAdapter(events);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        rvEvents = (RecyclerView) view.findViewById(R.id.rvEvents);
        rvEvents.setAdapter(aEvents);

        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));

        aEvents.setOnItemClickListener(new EventArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getContext(), EventDetailActivity.class);
                i.putExtra("eventId", events.get(position).getObjectId());
                i.putExtra("eventTitle", events.get(position).getTitle());
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
            }
        });

        rvEvents.addItemDecoration(new SpacesItemDecoration(20, 10));

        return view;
    }

    public void addAllEvents(List<Event> evnts) {
        events.addAll(evnts);
        aEvents.notifyDataSetChanged();
    }

    public void removeAllEvents() {
        events.clear();
        aEvents.notifyDataSetChanged();
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int verticalSpace;
        private int horizontalSpace;

        public SpacesItemDecoration(int verticalSpace, int horizontalSpace) {
            this.verticalSpace = verticalSpace;
            this.horizontalSpace = horizontalSpace;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = horizontalSpace;
            outRect.right = horizontalSpace;
            outRect.bottom = verticalSpace;

            // Add top margin only for the first item to avoid double space between items
            if(parent.getChildLayoutPosition(view) == 0)
                outRect.top = verticalSpace;
        }
    }
}