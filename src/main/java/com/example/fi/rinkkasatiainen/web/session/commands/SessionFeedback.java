package com.example.fi.rinkkasatiainen.web.session.commands;

import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionFeedback {

    public final int rating;
    public final String participantId;

    public SessionFeedback(@JsonProperty("rating") Integer rating, @JsonProperty("participantId") String participantId) {
        this.rating = rating;
        this.participantId = participantId;
    }

    public static ParticipantUUID asUUID(SessionFeedback feedback){
        return ParticipantUUID.from(feedback.participantId);
    }
}
