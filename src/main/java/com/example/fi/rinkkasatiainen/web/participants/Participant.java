package com.example.fi.rinkkasatiainen.web.participants;

import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Participant {

    @NotNull
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
