package com.example.fi.rinkkasatiainen;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.EventStore;

import java.util.*;

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
}
