package com.example.fi.rinkkasatiainen.web.session;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommand;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddSessionCommandHandlerShould {

    private static final UUID _UUID = UUID.randomUUID();
    public static final String TITLE = "LIVE Coding: CQRS + ES";

    @Test
    public void create_a_new_entity_and_return_uuid() throws Exception {
        Schedule schedule = mock(Schedule.class);

        when(schedule.newSessionUUID()).thenReturn( _UUID );
        AddSessionCommandHandler commandHandler = new AddSessionCommandHandler( schedule);

        UUID uuid = commandHandler.handles(new AddSessionCommand(TITLE));

        assertThat( uuid, equalTo(_UUID));
    }

    @Test
    public void saves_session() throws Exception {
        Schedule schedule = mock(Schedule.class);

        when(schedule.newSessionUUID()).thenReturn( _UUID );
        AddSessionCommandHandler commandHandler = new AddSessionCommandHandler( schedule);

        commandHandler.handles(new AddSessionCommand(TITLE));

        ArgumentCaptor<Session> sessionArgumentCaptor = ArgumentCaptor.forClass(Session.class);
        verify(schedule).save( argThat(equalTo(_UUID)), sessionArgumentCaptor.capture(), argThat(equalTo(0)));

        Session value = sessionArgumentCaptor.getValue();
        assertThat(value.getUncommittedChanges().get(0), equalTo(new SessionCreated(TITLE, _UUID)));

    }
}
