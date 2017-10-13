package com.example.fi.rinkkasatiainen.web.session;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommand;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.Session;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddSessionCommandHandlerShould {

    private static final UUID _UUID = UUID.randomUUID();
    public static final String TITLE = "LIVE Coding: CQRS + ES";

    @Test
    public void create_a_new_entity_and_return_uuid() throws Exception {
        Schedule schedule = mock(Schedule.class);

        when(schedule.newSession()).thenReturn( new Session(_UUID) );
        AddSessionCommandHandler commandHandler = new AddSessionCommandHandler( schedule);

        UUID uuid = commandHandler.handles(new AddSessionCommand(TITLE));

        assertThat( uuid, equalTo(_UUID));
    }
}
