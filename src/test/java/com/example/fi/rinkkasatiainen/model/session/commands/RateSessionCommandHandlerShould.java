package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.*;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RateSessionCommandHandlerShould {

    public static final SessionUUID UUID = SessionUUID.generate();
    private RateSessionCommandHandler commandHandler;
    private Schedule schedule;
    private EventPublisher eventPublisher;

    @Before
    public void setUp() throws Exception {
        schedule = mock(Schedule.class);
        eventPublisher = mock(EventPublisher.class);
        commandHandler = new RateSessionCommandHandler(schedule, eventPublisher);
    }

    @Test
    public void save_session() throws Exception {
        ParticipantUUID participantUUID = ParticipantUUID.generate();
        Session session = Session.load(new SessionCreated("LIVE CQRS+ES", UUID));
        int expectedVersion = session.getVersion();
        when(schedule.findSession(UUID)).thenReturn(session);

        commandHandler.handles( new RateSessionCommand(UUID, Stars.FOUR, participantUUID));

        verify(eventPublisher).save(argThat(equalTo(UUID)), any(Session.class), argThat(equalTo(expectedVersion)));
    }
}