package com.example.fi.rinkkasatiainen.web.session;

import com.example.fi.rinkkasatiainen.web.Command;

import java.util.UUID;

public class RegisterParticipantCommand implements Command {
    public final UUID uuid;
    private final UUID sessionId;

    public RegisterParticipantCommand(UUID uuid, UUID sessionId) {
        this.uuid= uuid;
        this.sessionId = sessionId;
    }
}
