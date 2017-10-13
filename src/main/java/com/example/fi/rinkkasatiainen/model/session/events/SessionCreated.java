package com.example.fi.rinkkasatiainen.model.session.events;

import com.example.fi.rinkkasatiainen.model.Event;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionCreated implements Event {
    private final String title;

    @JsonCreator
    public SessionCreated(@JsonProperty("title") String title) {
        this.title = title;
    }
}
