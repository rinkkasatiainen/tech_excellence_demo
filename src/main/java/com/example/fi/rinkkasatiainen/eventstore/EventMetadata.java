package com.example.fi.rinkkasatiainen.eventstore;

import com.example.fi.rinkkasatiainen.util.Struct;

class EventMetadata extends Struct {
    public String type;

    public EventMetadata(String type) {
        this.type = type;
    }

    public EventMetadata() {

    }
}
