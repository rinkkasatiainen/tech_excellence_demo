package com.example.fi.rinkkasatiainen.model.session.events;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.util.Struct;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionCreated implements Event {
    public final String title;
    public final SessionUUID uuid;

    @JsonCreator
    public SessionCreated(@JsonProperty("title") String title, @JsonProperty("uuid") SessionUUID uuid) {
        this.title = title;
        this.uuid = uuid;
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
        return new Struct.ForClass(this).toString();
    }
}
