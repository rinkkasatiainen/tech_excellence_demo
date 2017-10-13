package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;

public class ParticipantRegisteredEvent implements Event {
    private final SessionUUID sessionUuid;
    private final ParticipantUUID participantUUid;

    public ParticipantRegisteredEvent(SessionUUID sessionUuid, ParticipantUUID participantUUid) {
        this.sessionUuid = sessionUuid;
        this.participantUUid = participantUUid;
    }
}
