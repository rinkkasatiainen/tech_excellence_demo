package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.CreateEntityCommandHandler;
import com.example.fi.rinkkasatiainen.web.Handler;

import java.util.UUID;

public class AddSessionCommandHandler implements Handler<AddSessionCommand, SessionUUID> {
    private final Schedule schedule;

    public AddSessionCommandHandler(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public SessionUUID handles(AddSessionCommand addSessionCommand) {
        SessionUUID sessionUUID = schedule.newSessionUUID();
        Session session = Session.create(addSessionCommand.title, sessionUUID);

        schedule.save(sessionUUID, session, 0);

        return session.getUUID();
    }
}
