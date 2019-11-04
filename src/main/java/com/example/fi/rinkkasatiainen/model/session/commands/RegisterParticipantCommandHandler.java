package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.events.EventPublisher;
import com.example.fi.rinkkasatiainen.model.participants.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.participants.repositories.Audience;
import com.example.fi.rinkkasatiainen.model.session.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.repositories.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.Handler;
import com.example.fi.rinkkasatiainen.model.participants.Participant;

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
        final SessionUUID sessionUuid = command.sessionId;
        final ParticipantUUID participantId = command.participantId;

        final Session session = schedule.findSession(sessionUuid);
        final Integer originalVersion =  session.getVersion();

        // Step 2: register participant to the Session
        session.registerParticipant(participantId);

        // Step 3 (optional): get a Participant from the audience
        final Participant participant = audience.findParticipant(participantId);

        // Step 4: store to participant that session is registerd to
        eventPublisher.save( sessionUuid, session, originalVersion);
        audience.save(participantId, participant, participant.getVersion());
        return null;
    }
}
