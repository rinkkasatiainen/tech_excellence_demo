package com.example.fi.rinkkasatiainen.model.participants.repositories;

import com.example.fi.rinkkasatiainen.model.events.EventStore;
import com.example.fi.rinkkasatiainen.model.participants.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.participants.Participant;

public class Audience {
    public Audience(EventStore eventStore) {

    }

    public Participant findParticipant(ParticipantUUID uuid) {
        return new Participant(uuid);
    }

    public void save(ParticipantUUID sessionUUID, Participant participant, Integer expectedVersion) {

    }
}
