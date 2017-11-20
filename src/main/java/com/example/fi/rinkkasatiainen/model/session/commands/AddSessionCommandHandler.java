package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.EventPublisher;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.Handler;

public class AddSessionCommandHandler implements Handler<AddSessionCommand, SessionUUID> {
    private final Schedule schedule;
    private final EventPublisher eventPublisher;

    public AddSessionCommandHandler(Schedule schedule, EventPublisher eventPublisher) {
        this.schedule = schedule;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public SessionUUID handles(AddSessionCommand addSessionCommand) {
        SessionUUID sessionUUID = schedule.newSessionUUID();
        Session session = Session.create(addSessionCommand.title, sessionUUID);
        session.setDescription(addSessionCommand.description);

        eventPublisher.save(sessionUUID, session, 0);

        return session.getUUID();
    }
}
