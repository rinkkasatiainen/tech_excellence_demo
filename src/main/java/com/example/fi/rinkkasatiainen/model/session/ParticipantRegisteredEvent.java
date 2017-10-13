package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;

import java.util.UUID;

public class ParticipantRegisteredEvent implements Event {
    private final UUID sessionUuid;
    private final UUID participantUUid;

    public ParticipantRegisteredEvent(UUID sessionUuid, UUID participantUUid) {
        this.sessionUuid = sessionUuid;
        this.participantUUid = participantUUid;
    }
}
