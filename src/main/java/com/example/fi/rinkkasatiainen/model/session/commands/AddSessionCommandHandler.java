package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.EventPublisher;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.Handler;

import java.util.UUID;

public class AddSessionCommandHandler implements Handler<AddSessionCommand, SessionUUID> {
    private final Schedule schedule;
    private final EventPublisher eventPublisher;

    public AddSessionCommandHandler(Schedule schedule, EventPublisher eventPublisher) {
        this.schedule = schedule;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public SessionUUID handles(AddSessionCommand addSessionCommand) {
        //Step 1: Generate UUID
        SessionUUID sessionUuid = schedule.newSessionUUID();

        //Step 2: Generate Session Entity.
        Session session = Session.create(addSessionCommand.title, sessionUuid);

        //Step 3: add Description to the sessiono
        session.setDescription(addSessionCommand.description);

        //Step 4: save events to eventPublisher
        eventPublisher.save(sessionUuid, session, 0);

        //Step 5: return sessionUuid
        return sessionUuid;
    }
}
