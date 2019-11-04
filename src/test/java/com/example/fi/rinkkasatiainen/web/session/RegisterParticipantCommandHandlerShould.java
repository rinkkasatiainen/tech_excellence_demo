package com.example.fi.rinkkasatiainen.web.session;

import com.example.fi.rinkkasatiainen.model.events.EventPublisher;
import com.example.fi.rinkkasatiainen.model.participants.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.participants.repositories.Audience;
import com.example.fi.rinkkasatiainen.model.session.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.repositories.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommand;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.participants.Participant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


public class RegisterParticipantCommandHandlerShould {

    public static final SessionUUID sessionUUID = SessionUUID.generate();
    public static final ParticipantUUID participantUUID = ParticipantUUID.generate();
    public static final String TITLE = "TITLE";
    private RegisterParticipantCommandHandler commandHandler;
    private Audience audience;
    private Schedule schedule;
    private Session session;
    private Participant participant;
    private EventPublisher eventPublisher;
    @Before
    public void setUp() throws Exception {
        audience = mock(Audience.class);
        eventPublisher = mock(EventPublisher.class);
        participant = new Participant(participantUUID);
        when(audience.findParticipant(participantUUID)).thenReturn(participant);
        schedule = mock(Schedule.class);
        session = Session.load(Arrays.asList(new SessionCreated(TITLE, sessionUUID)));
        when( schedule.findSession(sessionUUID)).thenReturn(session);
        commandHandler = new RegisterParticipantCommandHandler(schedule, audience, eventPublisher);
    }

    @Test
    public void save_session_to() throws Exception {
        when( schedule.findSession(sessionUUID)).thenReturn(session);

        assertThat(session.getVersion(), equalTo(1));
        commandHandler.handles( new RegisterParticipantCommand(participantUUID, sessionUUID));

        ArgumentCaptor<Session> sessionArgumentCaptor = ArgumentCaptor.forClass(Session.class);

        verify(eventPublisher).save(eq(sessionUUID), sessionArgumentCaptor.capture(), Mockito.eq(1));

        Session modifiedSession = sessionArgumentCaptor.getValue();
        assertThat(modifiedSession.getVersion(), equalTo(2));
    }

    @Test
    public void save_participant() throws Exception {

        assertThat(session.getVersion(), equalTo(1));
        commandHandler.handles( new RegisterParticipantCommand(participantUUID, sessionUUID));

        verify(audience).save(participantUUID, participant, 1);
    }
}