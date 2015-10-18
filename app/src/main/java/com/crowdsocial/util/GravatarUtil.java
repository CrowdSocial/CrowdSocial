package com.crowdsocial.util;

public class GravatarUtil {

    private static final String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public static String getGravatarUrl(String email) {
        return GRAVATAR_URL + SignUtil.md5(email.toLowerCase().trim()) + "?s=100&d=wavatar";
    }

}
