package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.EventStore;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.Handler;

public class AddSessionCommandHandler implements Handler<AddSessionCommand, SessionUUID> {
    private final Schedule schedule;
    private final EventStore.EventPublisher eventPublisher;

    public AddSessionCommandHandler(Schedule schedule, EventStore.EventPublisher eventPublisher) {
        this.schedule = schedule;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public SessionUUID handles(AddSessionCommand addSessionCommand) {
        SessionUUID sessionUUID = schedule.newSessionUUID();
        Session session = Session.create(addSessionCommand.title, sessionUUID);

        eventPublisher.save(sessionUUID, session, 0);

        return session.getUUID();
    }
}
