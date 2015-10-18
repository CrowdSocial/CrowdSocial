package com.crowdsocial.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class ProfileUtil {

    private static final String USER = "user";

    public static boolean isUserLoggedIn(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return !TextUtils.isEmpty(prefs.getString(USER, null));
    }

    public static void loginUser(Context context, String email) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(USER, email).apply();
    }

    public static void logoutUser(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(USER, null).apply();
    }

}
