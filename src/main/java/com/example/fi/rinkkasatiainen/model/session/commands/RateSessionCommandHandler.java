package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.events.EventPublisher;
import com.example.fi.rinkkasatiainen.model.session.repositories.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.CommandHandler;

public class RateSessionCommandHandler implements CommandHandler<RateSessionCommand>{
    private final Schedule schedule;
    private final EventPublisher eventPublisher;

    public RateSessionCommandHandler(Schedule schedule, EventPublisher eventPublisher) {
        this.schedule = schedule;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Void handles(RateSessionCommand command) {
        // Step 1: find a session from Schedule
        final Session session = schedule.findSession(command.uuid);

        // Step 2: execute a command to rate the session
        final Integer originalVersion = session.getVersion();
        session.rate(command);

        // Step 3: save to eventStore
        eventPublisher.save( command.uuid, session, originalVersion);
        return null;
    }
}
