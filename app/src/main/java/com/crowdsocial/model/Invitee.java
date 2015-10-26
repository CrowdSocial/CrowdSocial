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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Invitee invitee = (Invitee) o;

        return !(getEmail() != null ? !getEmail().equals(invitee.getEmail()) : invitee.getEmail() != null);

    }

    @Override
    public int hashCode() {
        return getEmail() != null ? getEmail().hashCode() : 0;
    }
}
