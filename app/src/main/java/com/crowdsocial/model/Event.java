package com.crowdsocial.model;

import java.util.ArrayList;
import java.util.List;

public class Event {
    public String title;
    public String description;
    public String location;
    public int maxParticipants;
    public int participationAmount;
    public String imageUrl;

    //remove this method once we start working with real events
    public static List<Event> getDummyMyEvents() {
        ArrayList<Event> events = new ArrayList<>();

        Event event1 = new Event();
        event1.title = "Party event!";
        event1.description = "Party time description";
        event1.location = "San Francisco";
        event1.imageUrl = "http://lvs.luxury/wp-content/uploads/2015/05/IMG_1266Porche-event.jpg";

        events.add(event1);

        Event event2 = new Event();
        event2.title = "Sports event!";
        event2.description = "Sports time description";
        event2.location = "San Jose";
        event2.imageUrl = "http://www.teamsideline.com/Assets/267/sports.jpg";

        events.add(event2);



        return events;
    }


    public static List<Event> getDummyParticipatingEvents() {
        ArrayList<Event> events = new ArrayList<>();

        events.addAll(getDummyMyEvents());

        Event event3 = new Event();
        event3.title = "Trekking event!";
        event3.description = "Party time description";
        event3.location = "Los Angeles";
        event3.imageUrl = "http://www.holimites.com/pics/dolomites-trekking-av2-patitucciphoto-5.jpg";

        events.add(event3);

        return events;
    }
}
