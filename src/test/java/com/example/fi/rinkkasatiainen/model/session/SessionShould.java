package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.*;

public class SessionShould {

    public static final UUID UUID = java.util.UUID.randomUUID();
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
    public void create_a_new_session_with_title() throws Exception {
        assertThat(session.getTitle(), equalTo(TITLE));
    }

    @Test
    public void get_uncommitted_changes() throws Exception {
        assertThat(session.getUncommittedChanges(), hasItem( new SessionCreated(TITLE, UUID)));
    }
}