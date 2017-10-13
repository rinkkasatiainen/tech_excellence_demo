package com.example.fi.rinkkasatiainen.web.session.commands;

import com.example.fi.rinkkasatiainen.model.ParticipantUUID;

public class SessionFeedback {

    public final int rating;
    public final ParticipantUUID participant;

    public SessionFeedback(int rating, ParticipantUUID participant) {
        this.rating = rating;
        this.participant = participant;
    }
}
