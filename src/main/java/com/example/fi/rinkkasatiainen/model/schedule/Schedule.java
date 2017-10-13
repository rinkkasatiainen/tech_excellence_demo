package com.example.fi.rinkkasatiainen.model.schedule;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.EventStore;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class Schedule {
    private final Supplier<UUID> supplier;
    private final EventStore eventStore;

    public Schedule(Supplier<UUID> supplier, EventStore eventStore) {
        this.supplier = supplier;
        this.eventStore = eventStore;
    }


    public UUID newSessionUUID() {
        return supplier.get();
    }

    public Session findSession(UUID uuid) {
        // TODO AkS: COULD BE NO_EVENTS!
        List<Event> events = eventStore.findByUuid(uuid);
        return Session.load(events);
    }

    public SessionFeedbackResult findSessionFeeback(UUID uuid) {
        List<Event> events = eventStore.findByUuid(uuid);
        return SessionFeedbackResult.load(events);
    }

    public void save(UUID sessionUUID, Session session, Integer expectedVersion) {
        List<Event> uncommittedChanges = session.getUncommittedChanges();
        eventStore.saveEvents(sessionUUID, uncommittedChanges, expectedVersion);
        session.markChangesAsCommitted();

    }
}
