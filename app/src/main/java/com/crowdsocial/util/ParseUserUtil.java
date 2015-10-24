package com.crowdsocial.util;

import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class ParseUserUtil {

    public static void createUser(String email, String password, SignUpCallback callback) {
        ParseUser user = new ParseUser();
        //use email as username
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(callback);
    }

    public static void loginUser(String email, String password, LogInCallback callback) {
        ParseUser.logInInBackground(email, password, callback);
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
