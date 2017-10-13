package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;

import java.util.List;
import java.util.UUID;

public class Session {
    private UUID uuid;
    public Integer version;

    private Session() {
        version = 1;
    }

    public UUID getUUID() {
        return uuid;
    }

    public Session(UUID uuid) {
        this.uuid = uuid;
    }

    // Aggregate Root Methods

    public void addTitle(String title) {

    }

    // Primitive Obsession -> could do a thing here.
    public double getAverageRating() {
        return 0.0;
    }

    // TODO AkS: does actually nothing now.
    public static Session load(List<Event> events) {
        return new Session();
    }
}
