package com.example.fi.rinkkasatiainen.web.participants;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.AggregateRoot;

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
