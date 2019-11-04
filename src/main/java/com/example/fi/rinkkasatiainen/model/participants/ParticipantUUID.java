package com.example.fi.rinkkasatiainen.model.participants;

import com.example.fi.rinkkasatiainen.model.FeedbackerUUID;
import com.example.fi.rinkkasatiainen.util.Struct;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ParticipantUUID implements FeedbackerUUID {

    private final UUID uuid;

    @JsonCreator
    private ParticipantUUID(@JsonProperty("id") UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public static ParticipantUUID generate() {
        return new ParticipantUUID( UUID.randomUUID() );
    }

    public static ParticipantUUID from(String uuid) {
        return new ParticipantUUID( UUID.fromString(uuid) );
    }


    @Override
    public boolean equals(Object o) {
        return new Struct.ForClass(this).equals( o );
    }

    @Override
    public int hashCode() {
        return new Struct.ForClass(this).hashCode();
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
