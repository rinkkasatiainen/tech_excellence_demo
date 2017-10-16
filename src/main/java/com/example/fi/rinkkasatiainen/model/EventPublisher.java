package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.model.session.AggregateRoot;

import java.util.List;

public class EventPublisher {
    private final EventStore eventStore;

    public EventPublisher(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void save(FeedbackerUUID feedbackerUUID, AggregateRoot session, Integer expectedVersion) {
        List<Event> uncommittedChanges = session.getUncommittedChanges();
        eventStore.saveEvents(feedbackerUUID.getId(), uncommittedChanges, expectedVersion);
        session.markChangesAsCommitted();
    }
}
