package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.CreateEntityCommandHandler;

import java.util.UUID;

public class AddSessionCommandHandler implements CreateEntityCommandHandler<AddSessionCommand> {
    private final Schedule schedule;

    public AddSessionCommandHandler(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public UUID handles(AddSessionCommand addSessionCommand) {
        UUID sessionUUID = schedule.newSessionUUID();
        Session session = Session.create(addSessionCommand.title, sessionUUID);

        schedule.save(sessionUUID, session, 0);

        return session.getUUID();
    }
}
