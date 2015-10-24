package com.crowdsocial.util;

import com.parse.ParseException;

public class ParseErrorHandler {
    public static void handleError(ParseException e) {
        switch (e.getCode()) {
            case ParseException.INVALID_SESSION_TOKEN: handleInvalidSessionToken();
                break;
        }
    }

    private static void handleInvalidSessionToken() {
        ParseUserUtil.logoutUser();
    }
}