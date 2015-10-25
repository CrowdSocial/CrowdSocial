package com.crowdsocial.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Invitee")
public class Invitee extends ParseObject {

    private String name;
    private String email;
    private boolean accepted;

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getEmail() {
        return getString("email");
    }

    public void setEmail(String email) {
        put("email", email);
    }

    public boolean hasAccepted() {
        return getBoolean("accepted");
    }

    public void setAccepted(boolean accepted) {
        put("accepted", accepted);
    }
}
