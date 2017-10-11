package com.example.fi.rinkkasatiainen.web.queries;

import java.util.UUID;

public class SessionFeedbackResult {
    public static final Double NO_FEEDBACK = 0.0d;
    public final double averageRating;
    public final UUID uuid;

    public SessionFeedbackResult(double averageRating, UUID uuid) {
        this.averageRating = averageRating;
        this.uuid = uuid;
    }
}
