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
            this.uuid = event.uuid;
        }

        private void apply(SessionRated event) {
            if( Stars.ZERO.equals(event.stars)){
                ratings.remove(event.participantUUID);
            }
            this.ratings.put(event.participantUUID, event.stars.ordinal() );
        }
    }
}
