package com.example.fi.rinkkasatiainen.model.session.events;

import com.example.fi.rinkkasatiainen.model.Event;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SessionCreated implements Event {
    public final String title;
    public final UUID uuid;

    @JsonCreator
    public SessionCreated(@JsonProperty("title") String title, @JsonProperty("uuid") UUID uuid) {
        this.title = title;
        this.uuid = uuid;
    }
}
