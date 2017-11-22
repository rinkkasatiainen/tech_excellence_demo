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
        // Step 1: get a session from Schedule
        SessionUUID sessionId = command.sessionId;
        Session session = schedule.findSession(sessionId);
        Integer sessionVersion = session.getVersion();

        // Step 2: register participant to the Session
        ParticipantUUID participantId = command.participantId;
        session.registerParticipant(participantId);
        eventPublisher.save(sessionId, session, sessionVersion);

        // Step 3 (optional): get a Participant from the audience
        Participant participant = audience.findParticipant(participantId);
        Integer participantVersion = participant.getVersion();

        // Step 4: store to participant that session is registerd to
        participant.registerToEvent(sessionId);
        eventPublisher.save(participantId, participant, participantVersion);
        return null;
    }
}
