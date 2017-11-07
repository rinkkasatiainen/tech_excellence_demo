package com.example.fi.rinkkasatiainen.atdd;

import com.example.fi.rinkkasatiainen.FakeEventStore;
import com.example.fi.rinkkasatiainen.application.config.WebConfiguration;
import com.example.fi.rinkkasatiainen.model.Audience;
import com.example.fi.rinkkasatiainen.model.EventPublisher;
import com.example.fi.rinkkasatiainen.model.EventStore;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommandHandler;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionsRoute;

public class Wiring {
    private final WebConfiguration webConfiguration;
    private final EventStore eventStore;

    public Wiring() {
        eventStore = new FakeEventStore();
        webConfiguration = new WebConfiguration();
    }

    public RateSessionCommandHandler getRateSessionCommandHandler() {
        return webConfiguration.rateSessionCommandHandler(getSchedule(), getEventPublisher());
    }

    public RegisterParticipantCommandHandler getRegisterParticipantCommandHandler() {
        return webConfiguration.registerParticipantCommandHandler(getSchedule(), getAudience(), getEventPublisher());
    }

    public EventPublisher getEventPublisher() {
        return webConfiguration.getEventPublisher(eventStore);
    }

    public Audience getAudience() {
        return webConfiguration.audience(eventStore);
    }

    public Schedule getSchedule() {
        return webConfiguration.schedule( eventStore );
    }

    public SessionsRoute getSessionsRoute() {
        return new SessionsRoute(webConfiguration.addSessionCommandHandler(getSchedule(), getEventPublisher()));
    }
}
