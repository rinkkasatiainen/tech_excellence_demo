package com.example.fi.rinkkasatiainen.model.session.projections;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.EventLoader;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        private List<Integer> ratings = new ArrayList<>();
        private SessionUUID uuid;
        private final EventLoader loader;

        double getAverageRating() {
            if(ratings.size() == 0)
                return 0;
            return ratings.stream().reduce(0, (a, b) -> a+b) / ratings.size();
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
            this.ratings.add( event.stars.ordinal() );
        }
    }
}
