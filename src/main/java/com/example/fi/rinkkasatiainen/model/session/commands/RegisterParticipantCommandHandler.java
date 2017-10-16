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
        ParticipantUUID participantId = command.participantId;

        // Find Session from Schedule
        Session session = schedule.findSession(sessionId);
        Integer sessionVersion = session.getVersion();
        // Find Participant from Audience
        Participant participant = audience.findParticipant(participantId);
        Integer participantVersion = participant.getVersion();

        // Send messages to both Session and Participant objects
        session.registerParticipant(participantId);
        participant.registerToEvent(sessionId);

        // Save entities.
        // Note: instead of saving, it would be interesting to
        //    'register a eventPublisher' to AggregateRoot methods above -> saving events directly when
        //    events are dispatched / published
        // And I added eventPublisher that could be doing just that.
        eventPublisher.save(sessionId, session, sessionVersion);
        audience.save(participantId, participant, participantVersion);
        return null;
    }
}
