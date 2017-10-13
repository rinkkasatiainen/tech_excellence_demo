package com.example.fi.rinkkasatiainen.web.session.commands;

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
        Session session = schedule.newSession();
        session.addTitle( addSessionCommand.title );

        return session.getUUID();
    }
}
