package com.example.fi.rinkkasatiainen.model.session.projections;

import com.example.fi.rinkkasatiainen.model.*;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;

import java.util.*;

public class SessionFeedbackResult {
    private final EventSourceEntity eventSourceEntity;

    private SessionFeedbackResult(List<Event> events) {
        eventSourceEntity = null;
    }

    @Override
    public String toString() {
        return "SessionFeedbackResult" + eventSourceEntity.toString();
    }

    public double getAverageRating() {
        return eventSourceEntity.getAverageRating();
    }

    public SessionUUID getUuid() {
        return eventSourceEntity.getUuid();
    }

    public Integer getVersion() {
        return eventSourceEntity.getVersion();
    }


    public static SessionFeedbackResult load(List<Event> events) {
        SessionFeedbackResult result = new SessionFeedbackResult(events);
        return result;
    }








    private class EventSourceEntity{
        @Override
        public String toString() {
            return "{" +
                    "ratings=" + ratings +
                    ", uuid=" + uuid +
                    '}';
        }

        private Map<ParticipantUUID, Integer> ratings = new HashMap<>();
        private SessionUUID uuid;
        private final EventLoader loader;

        double getAverageRating() {
            return ((double)ratings.values().stream().reduce(0, (a, b) -> a+b)) / ratings.size();
        }

        SessionUUID getUuid() {
            return uuid;
        }

        public int getVersion() {
            return loader.getVersion();
        }











        EventSourceEntity(List<Event> events) {
            //Step 1: create EventLoader
            loader = null;
            //Step 2: register events
            // SessionCreated
            // SessionRated

            //Step 3: load the history.
        }


        private void apply(SessionCreated event) {
            // Step 1: add UUID
        }

        private void apply(SessionRated event) {
            // If stars is ZERO, remove the rating

            // Step 1: add rating to the has.
        }
    }
}
