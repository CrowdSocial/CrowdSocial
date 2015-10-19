package com.crowdsocial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.crowdsocial.R;
import com.crowdsocial.model.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventArrayAdapter extends ArrayAdapter<Event> {

    private static class ViewHolder {
        private ImageView ivEvent;
        private TextView tvLocation;
        private TextView tvTitle;
        private TextView tvDescription;
    }

    public EventArrayAdapter(Context context, List<Event> events) {
        super(context, android.R.layout.simple_list_item_1, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item_event, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.ivEvent = (ImageView) convertView.findViewById(R.id.ivEvent);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Event event = getItem(position);

        viewHolder.tvTitle.setText(event.getTitle());
        viewHolder.tvLocation.setText(event.getLocation());
        viewHolder.tvDescription.setText(event.getDescription());

        Picasso.with(getContext()).load(event.getImageUrl()).into(viewHolder.ivEvent);

        return convertView;
    }

}
