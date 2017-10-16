package com.example.fi.rinkkasatiainen.web.session.commands;

import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.web.participants.Participant;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipantDto {
    private final String participantId;

    public ParticipantDto(@JsonProperty("participantId") String participantId) {
        this.participantId = participantId;
    }

    public static ParticipantUUID getUuid(ParticipantDto dto) {
        return ParticipantUUID.from(dto.participantId);
    }
}
