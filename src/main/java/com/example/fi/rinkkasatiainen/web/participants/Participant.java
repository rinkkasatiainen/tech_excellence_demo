package com.example.fi.rinkkasatiainen.web.participants;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Participant {

    @NotNull
    public final UUID uuid;

    public Participant(UUID uuid){
        this.uuid = uuid;
    }

    public void registerToEvent(UUID sessionId) {

    }

    public Integer getVersion() {
        return 1;
    }
}
