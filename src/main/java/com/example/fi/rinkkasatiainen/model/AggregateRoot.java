package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.model.events.Event;

import java.util.List;

public interface AggregateRoot<U extends FeedbackerUUID> {
    U getUUID();

    Integer getVersion();

    List<Event> getUncommittedChanges();

    void markChangesAsCommitted();
}
