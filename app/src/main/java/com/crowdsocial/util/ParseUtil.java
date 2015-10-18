package com.crowdsocial.util;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class ParseUtil {

    public static void createUser(String email, String password) {
        ParseUser user = new ParseUser();
        //use email as username
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if(e == null) {
                    // Hooray! Let them use the app now.
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                }
            }
        });
    }

    public static void loginUser(String email, String password) {
        ParseUser.logInInBackground(email, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if(user != null) {
                    // Hooray! The user is logged in.
                } else {
                    // Login failed. Look at the ParseException to see what happened.
                }
            }
        });
    }

    public static ParseUser getLoggedInUser() {
        return ParseUser.getCurrentUser();
    }

    public static boolean isUserLoggedIn() {
        return getLoggedInUser() != null;
    }

    public static void logoutUser() {
        ParseUser.logOut();
    }
}
