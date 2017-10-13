package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.web.Command;

import java.util.UUID;

public class RegisterParticipantCommand implements Command {
    public final UUID participantId;
    protected final UUID sessionId;

    public RegisterParticipantCommand(UUID participantId, UUID sessionId) {
        this.participantId= participantId;
        this.sessionId = sessionId;
    }
}
