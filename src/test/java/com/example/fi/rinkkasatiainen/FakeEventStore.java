package com.example.fi.rinkkasatiainen;

import com.example.fi.rinkkasatiainen.model.events.Event;
import com.example.fi.rinkkasatiainen.model.events.EventStore;
import com.example.fi.rinkkasatiainen.model.session.SessionUUID;

import java.util.*;
import java.util.stream.Collectors;

public class FakeEventStore implements EventStore {

    Map<UUID, List<Event>> events;

    public FakeEventStore() {
        this.events = new HashMap<>();
    }

    @Override
    public List<Event> findByUuid(UUID random) {
        return events.getOrDefault(random, new ArrayList<>());
    }

    @Override
    public void saveEvents(UUID random, List<Event> uncommittedChanges, Integer lastVersion) {
        List<Event> eventList = findByUuid(random);
        eventList.addAll(uncommittedChanges);
        events.put(random, eventList);
    }

    @Override
    public List<Event> findAllByType(Class klass) {
        return events.values().stream(). //creates List<List<Event>>
                flatMap( List::stream ).
                filter( e -> e.getClass().equals(klass)).collect(Collectors.toList());
    }

    @Override
    public Map<SessionUUID, List<Event>> findAll(List<SessionUUID> uuids) {
        Map<SessionUUID, List<Event>> all = new HashMap<>();
        uuids.stream().
                forEachOrdered( uuid -> all.put( uuid, events.getOrDefault(uuid.getId(), new ArrayList<>()) )
                );

        return all;
    }
}
