package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.EventPublisher;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
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
        //Create input from Command
        SessionUUID uuid = command.uuid;

        Session session = schedule.findSession(uuid);
        Integer version = session.getVersion();
        //Call a domain entity method.
        session.rate( command );

        //publish events
        eventPublisher.save( uuid, session, version);
        return null;
    }
}
