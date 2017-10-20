package com.example.fi.rinkkasatiainen.eventstore;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.EventStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
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

        wrappedEventStore.save( storedEvents );
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
