package com.example.fi.rinkkasatiainen.atdd;

import com.example.fi.rinkkasatiainen.FakeEventStore;
import com.example.fi.rinkkasatiainen.application.config.WebConfiguration;
import com.example.fi.rinkkasatiainen.model.participants.repositories.Audience;
import com.example.fi.rinkkasatiainen.model.events.EventPublisher;
import com.example.fi.rinkkasatiainen.model.events.EventStore;
import com.example.fi.rinkkasatiainen.model.session.repositories.Schedule;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommandHandler;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionsRoute;

public class Wiring {
    public static final WebConfiguration webConfiguration = new WebConfiguration();
    public static final EventStore eventStore = new FakeEventStore();

    public Wiring() {
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
        return new SessionsRoute(
                webConfiguration.addSessionCommandHandler(getSchedule(), getEventPublisher())
        );
    }

    public static Schedule schedule() {
        return webConfiguration.schedule(eventStore);
    }

    public static Audience audience() {
        return webConfiguration.audience(eventStore);
    }

    public static AddSessionCommandHandler addSessionCommandHandler() {
        return webConfiguration.addSessionCommandHandler(schedule(), eventPublisher());
    }

    private static EventPublisher eventPublisher() {
        return webConfiguration.getEventPublisher(eventStore);
    }

    public static RegisterParticipantCommandHandler registerParticipantCommandHandler() {
        return webConfiguration.registerParticipantCommandHandler( schedule(), audience(), eventPublisher());
    }

    public static RateSessionCommandHandler rateSessionCommandHandler() {
        return webConfiguration.rateSessionCommandHandler(schedule(), eventPublisher());
    }
}
