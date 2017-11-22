package com.example.fi.rinkkasatiainen.web.participants;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.AggregateRoot;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
