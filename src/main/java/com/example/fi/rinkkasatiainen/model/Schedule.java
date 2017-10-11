package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.web.model.Session;

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


    public Session newSession() {
        return new Session(supplier.get());
    }

    public Session findSession(UUID uuid) {
        List<Event> events = eventStore.findByUuid(uuid);
        return Session.load(events);
    }
}
