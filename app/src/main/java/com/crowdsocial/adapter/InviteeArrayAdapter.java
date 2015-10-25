package com.crowdsocial.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.crowdsocial.R;

import java.util.List;

public class InviteeArrayAdapter extends ArrayAdapter<String> {

    private static class ViewHolder {
        private TextView tvNameEmail;
    }

    public InviteeArrayAdapter(Context context, List<String> events) {
        super(context, android.R.layout.simple_list_item_1, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.item_invitee, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tvNameEmail = (TextView) convertView.findViewById(R.id.tvNameEmail);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String invitee = getItem(position);

        viewHolder.tvNameEmail.setText(invitee);

        return convertView;
    }

}
