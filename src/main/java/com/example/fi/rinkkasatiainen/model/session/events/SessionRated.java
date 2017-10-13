package com.example.fi.rinkkasatiainen.model.session.events;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.Stars;
import com.example.fi.rinkkasatiainen.util.Struct;

public class SessionRated implements Event {
    private final SessionUUID uuid;
    public final Stars stars;

    public SessionRated(SessionUUID uuid, Stars stars) {
        this.uuid = uuid;
        this.stars = stars;
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
