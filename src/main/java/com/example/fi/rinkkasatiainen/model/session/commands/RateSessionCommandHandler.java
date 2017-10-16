package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.EventStore;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.web.CommandHandler;

public class RateSessionCommandHandler implements CommandHandler<RateSessionCommand>{
    private final Schedule schedule;
    private final EventStore eventStore;

    public RateSessionCommandHandler(Schedule schedule, EventStore eventStore) {
        this.schedule = schedule;
        this.eventStore = eventStore;
    }

    @Override
    public Void handles(RateSessionCommand command) {

        SessionUUID uuid = command.uuid;
        Session session = schedule.findSession(uuid);
        Integer version = session.getVersion();
        session.rate( command );

        eventStore.save( uuid, session, version);
        return null;
    }
}
