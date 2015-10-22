package com.crowdsocial.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("Event")
public class Event extends ParseObject {
    private String title;
    private String description;
    private String location;
    private int maxParticipants;
    private int participationAmount;
    private String imageUrl;
    private boolean isFree;
    private ParseUser user;
    private List<Invitee> invitees;

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title", title);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description", description);
    }

    public String getLocation() {
        return getString("location");
    }

    public void setLocation(String location) {
        put("location", location);
    }

    public int getMaxParticipants() {
        return getInt("maxParticipants");
    }

    public void setMaxParticipants(int maxParticipants) {
        put("maxParticipants", maxParticipants);
    }

    public int getParticipationAmount() {
        return getInt("participationAmount");
    }

    public void setParticipationAmount(int participationAmount) {
        put("participationAmount", participationAmount);
    }

    public String getImageUrl() {
        return getString("imageUrl");
    }

    public void setImageUrl(String imageUrl) {
        put("imageUrl", imageUrl);
    }

    public boolean isFree() {
        return getBoolean("isFree");
    }

    public void setIsFree(boolean isFree) {
        put("isFree", isFree);
    }

    public void setUser(ParseUser user) {
        put("user", user);
    }

    public ParseUser getUser() {
        return (ParseUser) getParseObject("user");
    }

    public List<Invitee> getInvitees() {
        return invitees == null ? new ArrayList<Invitee>() : invitees;
    }
}
