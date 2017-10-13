package com.example.fi.rinkkasatiainen.model.session.events;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.Stars;

public class SessionRated implements Event {
    public final Stars stars;

    public SessionRated(Stars stars) {
        this.stars = stars;
    }
}
