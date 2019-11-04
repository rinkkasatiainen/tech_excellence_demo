package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.participants.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.session.SessionUUID;
import com.example.fi.rinkkasatiainen.web.Command;

public class RegisterParticipantCommand implements Command {
    public final ParticipantUUID participantId;
    protected final SessionUUID sessionId;

    public RegisterParticipantCommand(ParticipantUUID participantId, SessionUUID sessionId) {
        this.participantId= participantId;
        this.sessionId = sessionId;
    }
}
