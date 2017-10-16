package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.Stars;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommand;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

public class SessionShould {

    public static final SessionUUID UUID = SessionUUID.generate();
    public static final String TITLE = "TITLE";
    private Session session;


    @Before
    public void setUp() throws Exception {
        session = Session.create(TITLE, UUID);
    }

    @Test
    public void create_a_new_session_with_version_1() throws Exception {
        //TODO Step 4.1 - creating event sourced entity creates with version 1
        assertThat(session.getVersion(), equalTo(1));
    }

    @Test
    public void get_uncommitted_changes() throws Exception {
        //TODO Step 4.2 creating an event publishes events.
        assertThat(session.getUncommittedChanges(), hasItem( new SessionCreated(TITLE, UUID)));
    }

    @Test
    public void should_rate() throws Exception {
        //TODO Step 4.3 - rating creates a uncommitted change - an event.
        ParticipantUUID participantUUID = ParticipantUUID.generate();
        session.rate(new RateSessionCommand(UUID, Stars.FOUR, participantUUID));
        assertThat(session.getUncommittedChanges(), hasItem(new SessionRated(UUID, Stars.FOUR, participantUUID)));
    }
}