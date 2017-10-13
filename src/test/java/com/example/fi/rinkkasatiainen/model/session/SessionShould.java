package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.Stars;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

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
        assertThat(session.getVersion(), equalTo(1));
    }

    @Test
    public void should_rate() throws Exception {
        session.rate(Stars.FOUR);
        assertThat(session.getUncommittedChanges(), hasItem(new SessionRated(UUID, Stars.FOUR)));
    }

    @Test
    public void get_uncommitted_changes() throws Exception {
        assertThat(session.getUncommittedChanges(), hasItem( new SessionCreated(TITLE, UUID)));
    }
}