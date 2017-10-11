package com.example.fi.rinkkasatiainen.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionCreated implements Event {
    private final String title;

    @JsonCreator
    public SessionCreated(@JsonProperty("title") String title) {
        this.title = title;
    }
}
