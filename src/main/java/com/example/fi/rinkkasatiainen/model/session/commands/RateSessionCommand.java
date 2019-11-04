package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.participants.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.session.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.Stars;
import com.example.fi.rinkkasatiainen.util.Struct;
import com.example.fi.rinkkasatiainen.web.Command;

public class RateSessionCommand implements Command {

    public final SessionUUID uuid;
    public final Stars stars;
    public final ParticipantUUID participantUUID;

    public RateSessionCommand(SessionUUID uuid, Stars stars, ParticipantUUID participantUUID) {
        this.uuid = uuid;
        this.stars = stars;
        this.participantUUID = participantUUID;
    }

    @Override
    public boolean equals(Object o) {
        return new Struct.ForClass(this).equals( o );
    }

    @Override
    public int hashCode() {
        return new Struct.ForClass(this).hashCode();
    }

    @Override
    public String toString() {
        return new Struct.ForClass(this).toString();
    }
}
