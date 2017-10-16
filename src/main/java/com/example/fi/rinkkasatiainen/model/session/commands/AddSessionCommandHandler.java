package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.EventStore;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.Handler;

public class AddSessionCommandHandler implements Handler<AddSessionCommand, SessionUUID> {
    private final Schedule schedule;
    private final EventStore eventStore;

    public AddSessionCommandHandler(Schedule schedule, EventStore eventStore) {
        this.schedule = schedule;
        this.eventStore = eventStore;
    }

    @Override
    public SessionUUID handles(AddSessionCommand addSessionCommand) {
        SessionUUID sessionUUID = schedule.newSessionUUID();
        Session session = Session.create(addSessionCommand.title, sessionUUID);

        eventStore.save(sessionUUID, session, 0);

        return session.getUUID();
    }
}
