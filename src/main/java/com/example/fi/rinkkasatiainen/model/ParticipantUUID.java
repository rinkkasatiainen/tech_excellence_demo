package com.example.fi.rinkkasatiainen.model;

import java.util.UUID;

public class ParticipantUUID {

    private final UUID uuid;

    private ParticipantUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getId() {
        return uuid;
    }

    public static ParticipantUUID generate() {
        return new ParticipantUUID( UUID.randomUUID() );
    }

    public static ParticipantUUID from(String uuid) {
        return new ParticipantUUID( UUID.fromString(uuid) );
    }
}
