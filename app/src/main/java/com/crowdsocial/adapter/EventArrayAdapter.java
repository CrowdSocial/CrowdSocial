package com.crowdsocial.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.crowdsocial.R;
import com.crowdsocial.model.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventArrayAdapter extends RecyclerView.Adapter<EventArrayAdapter.ViewHolder> {

    private List<Event> mEvents;
    Context context;

    public EventArrayAdapter(List<Event> events) {
        mEvents = events;
    }

    // Define listener member variable
    private OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivEvent;
        private TextView tvTitle;
        private TextView tvDescription;

        public ViewHolder(final View itemView) {
            super(itemView);

            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivEvent = (ImageView) itemView.findViewById(R.id.ivEvent);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }

    @Override
    public EventArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventView = inflater.inflate(R.layout.item_event, parent, false);
        return new ViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(EventArrayAdapter.ViewHolder viewHolder, int position) {
        Event event = mEvents.get(position);

        viewHolder.tvTitle.setText(event.getTitle());
        viewHolder.tvDescription.setText(event.getDescription());
        if(!TextUtils.isEmpty(event.getImageUrl())) {
            Picasso.with(context).load(event.getImageUrl()).into(viewHolder.ivEvent);
        }
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}
