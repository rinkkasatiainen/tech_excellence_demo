package com.example.fi.rinkkasatiainen.model.participants;

import com.example.fi.rinkkasatiainen.model.events.Event;
import com.example.fi.rinkkasatiainen.model.session.SessionUUID;
import com.example.fi.rinkkasatiainen.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;

public class Participant implements AggregateRoot<ParticipantUUID>{

    public final ParticipantUUID uuid;

    public Participant(ParticipantUUID uuid){
        this.uuid = uuid;
    }

    public void registerToEvent(SessionUUID sessionId) {

    }

    @Override
    public ParticipantUUID getUUID() {
        return this.uuid;
    }

    @Override
    public Integer getVersion() {
        return 1;
    }

    @Override
    public List<Event> getUncommittedChanges() {
        return new ArrayList<>();
    }

    @Override
    public void markChangesAsCommitted() {

    }
}
