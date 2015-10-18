package com.crowdsocial.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.crowdsocial.R;

public class LoginRegisterDialogFragment extends DialogFragment {

    private Switch swLoginReg;
    private EditText etEmail;
    private EditText etPassword;
    private Button btLoginReg;

    public static LoginRegisterDialogFragment newInstance() {
        LoginRegisterDialogFragment fragment = new LoginRegisterDialogFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_reg, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swLoginReg = (Switch) view.findViewById(R.id.swLoginReg);

        swLoginReg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btLoginReg.setText(R.string.login);
                } else {
                    btLoginReg.setText(R.string.register);
                }
            }
        });
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btLoginReg = (Button) view.findViewById(R.id.btLoginReg);

        if(swLoginReg.isChecked()) {
            btLoginReg.setText(R.string.login);
        } else {
            btLoginReg.setText(R.string.register);
        }
    }


}
