package com.crowdsocial.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.crowdsocial.R;

public class Step1Fragment extends Fragment {

    private OnEventDateClickListener eventDateListener;
    private OnCameraClick cameraClickListener;

    public static Step1Fragment newInstance() {
        Step1Fragment fragment = new Step1Fragment();
        return fragment;
    }

    public Step1Fragment() {
        // Required empty public constructor
    }

    public interface OnEventDateClickListener {
        public void OnEventDateClick();
    }

    public interface OnCameraClick {
        public void onLaunchCamera();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            eventDateListener = (OnEventDateClickListener) activity;
            cameraClickListener = (OnCameraClick) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " " +
                    "must implement OnEventDateClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_step1, container, false);

        EditText etDate = (EditText) v.findViewById(R.id.etDate);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDateListener.OnEventDateClick();
            }
        });

        ImageView ivEvent = (ImageView) v.findViewById(R.id.ivEvent);

        ivEvent.setImageResource(R.drawable.placeholder);

        ivEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraClickListener.onLaunchCamera();
            }
        });

        return v;
    }
}
