package com.crowdsocial.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crowdsocial.R;

public class Step1Fragment extends Fragment {

    public static Step1Fragment newInstance() {
        Step1Fragment fragment = new Step1Fragment();
        return fragment;
    }

    public Step1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step1, container, false);
    }
}
