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
import android.widget.Toast;

import com.crowdsocial.R;
import com.crowdsocial.util.ParseUserUtil;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginRegisterDialogFragment extends DialogFragment {

    private Switch swLoginReg;
    private EditText etEmail;
    private EditText etPassword;
    private Button btLoginReg;

    public interface LoginRegisterDialogListener {
        void onFinishLoginRegisterDialog();
    }


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

        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btLoginReg = (Button) view.findViewById(R.id.btLoginReg);

        btLoginReg.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (swLoginReg.isChecked()) {

                        LogInCallback callback = new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if(e != null) {
                                    Toast.makeText(
                                            getContext(), R.string.invalid_login, Toast.LENGTH_SHORT).show();
                                } else {
                                    LoginRegisterDialogListener listener =
                                            (LoginRegisterDialogListener) getActivity();
                                    listener.onFinishLoginRegisterDialog();
                                    dismiss();
                                }
                            }
                        };

                        ParseUserUtil.loginUser(etEmail.getText().toString(),
                                etPassword.getText().toString(),
                                callback);
                    } else {

                        SignUpCallback callback = new SignUpCallback() {
                            public void done(ParseException e) {
                                if(e != null) {
                                    Toast.makeText(
                                            getContext(), R.string.invalid_registration, Toast.LENGTH_SHORT).show();
                                } else {
                                    LoginRegisterDialogListener listener =
                                            (LoginRegisterDialogListener) getActivity();
                                    listener.onFinishLoginRegisterDialog();
                                    dismiss();
                                }
                            }
                        };

                        ParseUserUtil.createUser(etEmail.getText().toString(),
                                etPassword.getText().toString(),
                                callback);
                    }

                }
            }
        );

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

        if (swLoginReg.isChecked()) {
            btLoginReg.setText(R.string.login);
        } else {
            btLoginReg.setText(R.string.register);
        }
    }
}
