package com.example.fi.rinkkasatiainen.web.participants;

import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class Participant {

    public final ParticipantUUID uuid;

    public Participant(ParticipantUUID uuid){
        this.uuid = uuid;
    }

    public void registerToEvent(SessionUUID sessionId) {

    }

    public Integer getVersion() {
        return 1;
    }
}
