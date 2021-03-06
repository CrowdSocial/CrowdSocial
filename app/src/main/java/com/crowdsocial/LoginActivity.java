package com.crowdsocial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdsocial.activity.EventListActivity;
import com.crowdsocial.util.ParseUserUtil;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private Switch swLoginReg;
    private EditText etEmail;
    private EditText etPassword;
    private Button btLoginReg;
    private TextView tvLogin;
    private TextView tvRegister;

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
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLoginReg = (Button) findViewById(R.id.btLoginReg);
        swLoginReg = (Switch) findViewById(R.id.swLoginReg);
        swLoginReg.setChecked(true);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvLogin.setTextColor(getResources().getColor(R.color.white_transparent));
        tvRegister.setTextColor(getResources().getColor(R.color.secondary_text));

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
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void onLoginClick(View view) {
        swLoginReg.setChecked(true);
        tvLogin.setTextColor(getResources().getColor(R.color.white_transparent));
        tvRegister.setTextColor(getResources().getColor(R.color.secondary_text));
    }

    public void onRegisterClick(View view) {
        swLoginReg.setChecked(false);
        tvRegister.setTextColor(getResources().getColor(R.color.white_transparent));
        tvLogin.setTextColor(getResources().getColor(R.color.secondary_text));
    }
}
