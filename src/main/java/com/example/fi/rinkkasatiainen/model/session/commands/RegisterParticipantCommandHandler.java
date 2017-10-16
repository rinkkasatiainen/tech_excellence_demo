package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.*;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.Handler;
import com.example.fi.rinkkasatiainen.web.participants.Participant;

public class RegisterParticipantCommandHandler implements Handler<RegisterParticipantCommand, Void >{
    private Schedule schedule;
    private final Audience audience;
    private final EventPublisher eventPublisher;

    public RegisterParticipantCommandHandler(Schedule schedule, Audience audience, EventPublisher eventPublisher) {
        this.schedule = schedule;
        this.audience = audience;
        this.eventPublisher = eventPublisher;
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

        eventPublisher.save(sessionId, session, sessionVersion);
        audience.save(participantId, participant, participantVersion);
        return null;
    }
}
