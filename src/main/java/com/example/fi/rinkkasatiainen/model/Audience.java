package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.web.participants.Participant;

public class Audience {
    public Audience(EventStore eventStore) {

    }

    public Participant findParticipant(ParticipantUUID uuid) {
        return new Participant(uuid);
    }

    public void save(ParticipantUUID sessionUUID, Participant participant, Integer expectedVersion) {

    }
}
