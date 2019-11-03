package com.example.fi.rinkkasatiainen.eventstore;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.EventStore;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PersistentEventStore implements EventStore {
    private final ObjectMapper objectMapper;
    private final JpaEventStore wrappedEventStore;

    public PersistentEventStore(ObjectMapper objectMapper, JpaEventStore wrappedEventStore) {
        this.objectMapper = objectMapper;
        this.wrappedEventStore = wrappedEventStore;
    }

    @Override
    public List<Event> findByUuid(UUID streamUuid) {
        List<StoredEvent> events  = wrappedEventStore.findAllByLeastSignificantBitsAndMostSignificantBitsOrderByVersionAsc(
                streamUuid.getLeastSignificantBits(),
                streamUuid.getMostSignificantBits()
        );

        return listEvents(events);
    }

    private List<Event> listEvents(List<StoredEvent> events) {
        return events.stream().map(
                storedEvent -> deserialize(storedEvent.getData(), storedEvent.getMetadata())
        ).collect(Collectors.toList());
    }

    @Override
    public void saveEvents(UUID streamUUID, List<Event> uncommittedChanges, Integer lastVersion) {
        final Integer[] nextVersion = {lastVersion + 1};
        List<StoredEvent> storedEvents = uncommittedChanges.stream().map(
                event -> new StoredEvent(streamUUID, serialize(getMetadata(event)), serialize(event), nextVersion[0]++)
        ).collect(Collectors.toList());

        wrappedEventStore.saveAll( storedEvents );
    }

    @Override
    public List<Event> findAllByType(Class klass) {
        EventMetadata metadata = new EventMetadata( klass.getName() );

        List<StoredEvent> events  = wrappedEventStore.findAllByMetadata( serialize( metadata ) );
        return listEvents(events);
    }

    @Override
    public Map<SessionUUID, List<Event>> findAll(List<SessionUUID> uuids) {

        Map<SessionUUID, List<Event>> bits = new HashMap<>();

        uuids.stream().forEachOrdered( uuid -> {
            long leastSignificantBits = uuid.getId().getLeastSignificantBits();
            long mostSignificantBits = uuid.getId().getMostSignificantBits();
            List<StoredEvent> allForUUid = wrappedEventStore.findAllByLeastSignificantBitsAndMostSignificantBitsOrderByVersionAsc(leastSignificantBits, mostSignificantBits);
            bits.put( uuid, listEvents(allForUUid));
        });

        return bits;
    }

    private Event deserialize(String dataJson, String metadataJson) {
        try {
            EventMetadata metadata = objectMapper.readValue(metadataJson, EventMetadata.class);
            return (Event) objectMapper.readValue(dataJson, Class.forName(metadata.type));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static EventMetadata getMetadata(Event event) {
        EventMetadata meta = new EventMetadata();
        meta.type = event.getClass().getName();
        return meta;
    }


    private String serialize(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize: " + event, e);
        }
    }
}
