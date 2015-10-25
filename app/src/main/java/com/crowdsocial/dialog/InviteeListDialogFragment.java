package com.crowdsocial.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.crowdsocial.R;
import com.crowdsocial.adapter.InviteeArrayAdapter;
import com.crowdsocial.model.Invitee;

import java.util.ArrayList;
import java.util.List;

public class InviteeListDialogFragment extends DialogFragment {

    private ArrayList<String> invitees = new ArrayList<>();
    private InviteeArrayAdapter aInvitees;
    private ListView lvInvitees;


    public static InviteeListDialogFragment newInstance(List<Invitee> invitees) {
        InviteeListDialogFragment fragment = new InviteeListDialogFragment();

        Bundle args = new Bundle();
        args.putStringArrayList("invitees", getInvitees(invitees));
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_invitee, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        invitees = getArguments().getStringArrayList("invitees");
        lvInvitees = (ListView) view.findViewById(R.id.lvInvitees);
        aInvitees = new InviteeArrayAdapter(this.getContext(), invitees);
        lvInvitees.setAdapter(aInvitees);
    }

    private static ArrayList<String> getInvitees(List<Invitee> invitees) {
        ArrayList<String> inviteeList = new ArrayList<>();
        for(Invitee i : invitees) {
            if(!TextUtils.isEmpty(i.getName())) {
                inviteeList.add(i.getName());
            } else {
                inviteeList.add(i.getEmail());
            }
        }
        return inviteeList;
    }
}
