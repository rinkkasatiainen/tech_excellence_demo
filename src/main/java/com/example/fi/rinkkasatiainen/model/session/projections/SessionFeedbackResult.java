package com.example.fi.rinkkasatiainen.model.session.projections;

import com.example.fi.rinkkasatiainen.model.*;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;

import java.util.*;

public class SessionFeedbackResult {
    private final EventSourceEntity eventSourceEntity;

    private SessionFeedbackResult(List<Event> events) {
        eventSourceEntity = new EventSourceEntity(events);
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
            if(ratings.size() == 0)
                return 0;
            return ((double)ratings.values().stream().reduce(0, (a, b) -> a+b)) / ratings.size();
        }

        SessionUUID getUuid() {
            return uuid;
        }

        public int getVersion() {
            return loader.getVersion();
        }











        EventSourceEntity(List<Event> events) {
            loader = new EventLoader();
            loader.register(SessionCreated.class, this::apply);
            loader.register(SessionRated.class, this::apply);
            //register

            events.forEach(loader::apply);
        }
        private void apply(SessionCreated event) {
            //Add UUID to the stats.
        }

        private void apply(SessionRated event) {
            // If stars is ZERO, remove the rating
            // add rating to the has.
        }
    }
}
