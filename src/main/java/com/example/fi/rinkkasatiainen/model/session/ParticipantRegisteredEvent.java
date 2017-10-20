package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipantRegisteredEvent implements Event {
    public final SessionUUID sessionUuid;
    public final ParticipantUUID participantUuid;

    @JsonCreator
    public ParticipantRegisteredEvent(
            @JsonProperty("sessionUuid") SessionUUID sessionUuid,
            @JsonProperty("participantUuid") ParticipantUUID participantUuid
    ) {
        this.sessionUuid = sessionUuid;
        this.participantUuid = participantUuid;
    }
}
