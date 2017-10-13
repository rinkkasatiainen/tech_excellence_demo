package com.example.fi.rinkkasatiainen.model.session.projections;

import com.example.fi.rinkkasatiainen.model.Event;

import java.util.List;
import java.util.UUID;

public class SessionFeedbackResult {
    public static final Double NO_FEEDBACK = 0.0d;
    public final double averageRating;
    public final UUID uuid;
    public Integer version;

    public SessionFeedbackResult(double averageRating, UUID uuid) {
        this.averageRating = averageRating;
        this.uuid = uuid;
    }

    private SessionFeedbackResult() {
        averageRating = 5.0;
        uuid = UUID.randomUUID();
        version = 2;
    }

    public static SessionFeedbackResult load(List<Event> events) {
        return new SessionFeedbackResult();
    }
}
