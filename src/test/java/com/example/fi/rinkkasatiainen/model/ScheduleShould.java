package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.model.session.SessionDetails;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionDescriptionAdded;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class ScheduleShould {

    public static final String TITLE = "Title";
    public static final SessionUUID UUID = SessionUUID.generate();
    public static final String DESCRIPTION = "desck";
    public static final String ANOTHER_TITLE = "ANOTHER session";
    private Supplier<SessionUUID> randomUUIDSupplier = () -> UUID;
    private Schedule schedule;
    private EventStore eventStore;

    @Before
    public void setUp() throws Exception {
        eventStore = mock(EventStore.class);
        schedule = new Schedule(randomUUIDSupplier, eventStore);
    }

    @Test
    public void create_new_session() throws Exception {
        SessionUUID session = schedule.newSessionUUID();

        assertThat( session, equalTo(UUID));
    }

    @Test
    public void find_session() throws Exception {
        when(eventStore.findByUuid(UUID.getId())).thenReturn(Arrays.asList(new SessionCreated(TITLE, UUID) ));

        Session s = schedule.findSession(UUID);
        assertThat(s.getVersion(), equalTo(1));
    }

    @Test
    public void find_session_feedback() throws Exception {
        ParticipantUUID participantUUID = ParticipantUUID.generate();
        when(eventStore.findByUuid(UUID.getId())).thenReturn(Arrays.asList(new SessionCreated(TITLE, UUID), new SessionRated(Stars.FIVE, participantUUID)));

        SessionFeedbackResult result = schedule.findSessionFeeback(UUID);
        assertThat(result.getVersion(), equalTo(2));
        assertThat(result.getAverageRating(), equalTo(5.0));
    }

    @Test
    public void find_session_details_of_all_sessions() throws Exception {
        ParticipantUUID participantUUID = ParticipantUUID.generate();
        SessionUUID anotherSession = SessionUUID.generate();

        when(eventStore.findAllByType(SessionCreated.class)).thenReturn(Arrays.asList(
                new SessionCreated(TITLE, UUID),
                new SessionCreated(TITLE, anotherSession)
        ));

        Map<SessionUUID, SessionDetails> expectedMap = new HashMap<>();
        List<Event> uuid_events = Arrays.asList(new SessionCreated(TITLE, UUID),
                new SessionDescriptionAdded(DESCRIPTION, UUID)
        );
        expectedMap.put(UUID, SessionDetails.load(
                uuid_events));

        List<Event> secondEvens = Arrays.asList(new SessionRated(Stars.FIVE, participantUUID),
                new SessionCreated(ANOTHER_TITLE, anotherSession),
                new SessionDescriptionAdded(DESCRIPTION, anotherSession)
        );
        expectedMap.put(anotherSession, SessionDetails.load(
                secondEvens));

        Map<SessionUUID, List<Event>> events = new HashMap<>();
        events.put(UUID, uuid_events);
        events.put(anotherSession, secondEvens);
        when(eventStore.findAll(Arrays.asList(UUID, anotherSession))).thenReturn(events);

        List<SessionDetails> allSessions = schedule.findAllSessions();
        assertThat(allSessions, hasItem(expectedMap.get(UUID)));
        assertThat(allSessions, hasItem(expectedMap.get(anotherSession)));
    }
}