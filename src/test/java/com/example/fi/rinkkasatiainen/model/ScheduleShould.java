package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.*;

public class ScheduleShould {


    public static final String TITLE = "Title";
    public static final SessionUUID UUID = SessionUUID.generate();
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
        when(eventStore.findByUuid(UUID.getId())).thenReturn(Arrays.asList(new SessionCreated(TITLE, UUID), new SessionRated(UUID, Stars.FIVE, participantUUID)));

        SessionFeedbackResult result = schedule.findSessionFeeback(UUID);
        assertThat(result.getVersion(), equalTo(2));
        assertThat(result.getAverageRating(), equalTo(5.0));
    }


    @Test
    public void save_session_to_event_store() throws Exception {
        Session session = Session.create(TITLE, UUID);
        schedule.save(UUID, session, 0);

//        void saveEvents(UUID uuid, List<Event> uncommittedChanges, int expectedVersion);
        ArgumentCaptor<List> listArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(eventStore).saveEvents(argThat(equalTo(UUID.getId())), listArgumentCaptor.capture(), argThat(equalTo(0)));

        List value = listArgumentCaptor.getValue();
        SessionCreated sessionCreated = (SessionCreated) value.get(0);

        assertThat(sessionCreated.title, equalTo(TITLE));
    }

}