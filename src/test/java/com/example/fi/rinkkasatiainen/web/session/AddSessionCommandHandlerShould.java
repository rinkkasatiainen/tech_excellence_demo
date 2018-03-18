package com.example.fi.rinkkasatiainen.web.session;

import com.example.fi.rinkkasatiainen.model.EventPublisher;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommand;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddSessionCommandHandlerShould {

    public static final SessionUUID UUID = SessionUUID.generate();
    public static final String TITLE = "LIVE Coding: CQRS + ES";
    private EventPublisher eventPublisher;
    private String description = "DESCRIption of the talk";

    @Test
    public void create_a_new_entity_and_return_uuid() throws Exception {
        Schedule schedule = mock(Schedule.class);
        eventPublisher = mock(EventPublisher.class);

        when(schedule.newSessionUUID()).thenReturn(UUID);
        AddSessionCommandHandler commandHandler = new AddSessionCommandHandler( schedule, eventPublisher);

        SessionUUID uuid = commandHandler.handles(new AddSessionCommand(TITLE, description));

        assertThat( uuid, equalTo(UUID));
    }

    @Test
    public void saves_session() throws Exception {
        Schedule schedule = mock(Schedule.class);
        eventPublisher = mock(EventPublisher.class);

        when(schedule.newSessionUUID()).thenReturn(UUID);
        AddSessionCommandHandler commandHandler = new AddSessionCommandHandler( schedule, eventPublisher);

        commandHandler.handles(new AddSessionCommand(TITLE, description));

        ArgumentCaptor<Session> sessionArgumentCaptor = ArgumentCaptor.forClass(Session.class);
        verify(eventPublisher).save( argThat(equalTo(UUID)), sessionArgumentCaptor.capture(), argThat(equalTo(0)));

        Session value = sessionArgumentCaptor.getValue();
        assertThat(value.getUncommittedChanges().get(0), equalTo(new SessionCreated(TITLE, UUID)));

    }
}
