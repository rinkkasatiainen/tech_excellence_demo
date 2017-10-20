package com.example.fi.rinkkasatiainen.eventstore;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.util.Struct;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TestEvent extends Struct implements Event {
    private final String title;

    @JsonCreator
    public TestEvent(@JsonProperty("title") String title) {
        this.title = title;
    }
}
