package com.example.fi.rinkkasatiainen.eventstore;

import com.example.fi.rinkkasatiainen.application.config.EventStoreObjectMapper;
import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersistentEventStoreShould {


    public static final UUID UUID = java.util.UUID.randomUUID();
    public static final String TITLE = "TITLE";
    private JpaEventStore wrappedEventStore;
    private ObjectMapper objectMapper;
    private PersistentEventStore eventStore;

    @Before
    public void setUp() throws Exception {
        wrappedEventStore = mock(JpaEventStore.class);
        objectMapper = new EventStoreObjectMapper().getObjectMapper();
        eventStore = new PersistentEventStore(objectMapper, wrappedEventStore);
    }

    @Test
    public void find_events_by_uuid() throws Exception {
        when(wrappedEventStore.findAllByLeastSignificantBitsAndMostSignificantBitsOrderByVersionAsc(
                UUID.getLeastSignificantBits(),
                UUID.getMostSignificantBits()
        )).thenReturn(new ArrayList<>());

        eventStore.findByUuid(UUID);

        verify(wrappedEventStore).findAllByLeastSignificantBitsAndMostSignificantBitsOrderByVersionAsc(
                UUID.getLeastSignificantBits(),
                UUID.getMostSignificantBits()
        );
    }

    @Test
    public void should_return_an_event() throws Exception {
        String metadata = "{\"type\":\"" + TestEvent.class.getCanonicalName() + "\"}";
        String data = "{\"title\":\"" + TITLE + "\"}";
        when(wrappedEventStore.findAllByLeastSignificantBitsAndMostSignificantBitsOrderByVersionAsc(
                UUID.getLeastSignificantBits(),
                UUID.getMostSignificantBits()
        )).thenReturn(Arrays.asList(
                new StoredEvent(UUID, metadata, data, 0)
        ));

        List<Event> events = eventStore.findByUuid(UUID);

        assertThat( events, hasItem(new TestEvent(TITLE)));

    }

}