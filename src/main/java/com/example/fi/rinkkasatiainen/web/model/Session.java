package com.example.fi.rinkkasatiainen.web.model;

import java.util.UUID;

public class Session {
    private final UUID uuid;

    public UUID getUUID() {
        return uuid;
    }

    public Session(UUID uuid) {
        this.uuid = uuid;
    }

    // Aggregate Root Methods

    public void addTitle(String title) {

    }
}
