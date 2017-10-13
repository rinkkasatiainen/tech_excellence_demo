package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.ParticipantUUID;

import java.util.UUID;

public class ParticipantRegisteredEvent implements Event {
    private final UUID sessionUuid;
    private final ParticipantUUID participantUUid;

    public ParticipantRegisteredEvent(UUID sessionUuid, ParticipantUUID participantUUid) {
        this.sessionUuid = sessionUuid;
        this.participantUUid = participantUUid;
    }
}
