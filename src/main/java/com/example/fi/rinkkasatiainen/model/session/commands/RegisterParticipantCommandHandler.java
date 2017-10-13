package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.Audience;
import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.Handler;
import com.example.fi.rinkkasatiainen.web.participants.Participant;

import java.util.UUID;

public class RegisterParticipantCommandHandler implements Handler<RegisterParticipantCommand, Void >{
    private Schedule schedule;
    private final Audience audience;

    public RegisterParticipantCommandHandler(Schedule schedule, Audience audience) {
        this.schedule = schedule;
        this.audience = audience;
    }

    @Override
    public Void handles(RegisterParticipantCommand command) {
        SessionUUID sessionId = command.sessionId;
        Session session = schedule.findSession(sessionId);
        Integer sessionVersion = session.getVersion();

        ParticipantUUID participantId = command.participantId;
        session.registerParticipant(participantId);

        Participant participant = audience.findParticipant(participantId);
        Integer participantVersion = participant.getVersion();
        participant.registerToEvent(sessionId);

        schedule.save(sessionId, session, sessionVersion);
        audience.save(participantId, participant, participantVersion);
        return null;
    }
}
