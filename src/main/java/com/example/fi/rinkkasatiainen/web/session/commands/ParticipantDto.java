package com.example.fi.rinkkasatiainen.web.session.commands;

import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipantDto {
    public final ParticipantUUID uuid;

    public ParticipantDto(@JsonProperty("participantId") String id) {
        this.uuid = ParticipantUUID.from(id);
    }
}
