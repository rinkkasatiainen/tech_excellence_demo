package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.web.participants.Participant;

import java.util.UUID;

public class Audience {
    public Audience(EventStore eventStore) {

    }

    public Participant findParticipant(UUID uuid) {
        return null;
    }

    public void save(UUID sessionUUID, Participant participant, Integer expectedVersion) {

    }
}
