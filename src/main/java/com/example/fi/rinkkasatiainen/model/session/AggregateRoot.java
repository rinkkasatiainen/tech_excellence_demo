package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.FeedbackerUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;

import java.util.List;

public interface AggregateRoot<U extends FeedbackerUUID> {
    SessionUUID getUUID();

    Integer getVersion();

    List<Event> getUncommittedChanges();

    void markChangesAsCommitted();
}
