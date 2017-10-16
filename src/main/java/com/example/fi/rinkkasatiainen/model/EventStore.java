package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.AggregateRoot;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    List<Event> findByUuid(UUID random);

    void saveEvents(UUID random, List<Event> uncommittedChanges, Integer lastVersion);

    default void save(FeedbackerUUID feedbackerUUID, AggregateRoot session, Integer expectedVersion) {
        new EventPublisher(this).save(feedbackerUUID, session, expectedVersion);
//        List<Event> uncommittedChanges = session.getUncommittedChanges();
//        saveEvents(feedbackerUUID.getId(), uncommittedChanges, expectedVersion);
//        session.markChangesAsCommitted();

    }

    class EventPublisher{
        private final EventStore eventStore;

        public EventPublisher(EventStore eventStore) {
            this.eventStore = eventStore;
        }

        void save(FeedbackerUUID feedbackerUUID, AggregateRoot session, Integer expectedVersion) {
            List<Event> uncommittedChanges = session.getUncommittedChanges();
            eventStore.saveEvents(feedbackerUUID.getId(), uncommittedChanges, expectedVersion);
            session.markChangesAsCommitted();
        }
    }
}
