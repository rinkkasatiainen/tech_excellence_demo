package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.util.Struct;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SessionUUID implements FeedbackerUUID {
    private final UUID uuid;

    @JsonCreator
    private SessionUUID(@JsonProperty("id") UUID uuid) {
        this.uuid = uuid;
    }

    public static SessionUUID from(String uuid) {
        return new SessionUUID(UUID.fromString(uuid));
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public static SessionUUID generate() {
        return new SessionUUID( UUID.randomUUID() );
    }

    @Override
    public boolean equals(Object o) {
        return new Struct.ForClass(this).equals( o );
    }

    @Override
    public int hashCode() {
        return new Struct.ForClass(this).hashCode();
    }

//    @Override
//    public boolean equals(Object o) {
//        return uuid.equals( o );
//    }
//
//    @Override
//    public int hashCode() {
//        return uuid.hashCode();
//    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
