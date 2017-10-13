package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.CommandHandler;

import java.util.UUID;

public class RateSessionCommandHandler implements CommandHandler<RateSessionCommand>{
    private final Schedule schedule;

    public RateSessionCommandHandler(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public Void handles(RateSessionCommand command) {

        UUID uuid = command.uuid;
        Session session = schedule.findSession(uuid);
        Integer version = session.getVersion();
        session.rate( command.stars );

        schedule.save( uuid, session, version);
        return null;
    }
}
