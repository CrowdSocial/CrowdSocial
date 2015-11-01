package com.crowdsocial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.crowdsocial.activity.EventListActivity;
import com.crowdsocial.util.ParseUserUtil;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private ToggleButton swLoginReg;
    private EditText etEmail;
    private EditText etPassword;
    private Button btLoginReg;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(ParseUserUtil.isUserLoggedIn()) {
            startEventListActivity();
        }
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLoginReg = (Button) findViewById(R.id.btLoginReg);

        btLoginReg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (swLoginReg.isChecked()) {

                            LogInCallback callback = new LogInCallback() {
                                public void done(ParseUser user, ParseException e) {
                                    if(e != null) {
                                        Toast.makeText(
                                                LoginActivity.this, R.string.invalid_login, Toast.LENGTH_SHORT).show();
                                    } else {
                                        startEventListActivity();
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
                                                LoginActivity.this, R.string.invalid_registration, Toast.LENGTH_SHORT).show();
                                    } else {
                                        startEventListActivity();
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

        swLoginReg = (ToggleButton) findViewById(R.id.swLoginReg);
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

    private void startEventListActivity() {
        Intent i = new Intent(this, EventListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}
