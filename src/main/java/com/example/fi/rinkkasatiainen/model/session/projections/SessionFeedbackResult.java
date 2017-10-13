package com.example.fi.rinkkasatiainen.model.session.projections;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;

import java.util.*;
import java.util.function.Consumer;

public class SessionFeedbackResult {
    private final EventSourceEntity eventSourceEntity;

    private SessionFeedbackResult(List<Event> events) {

        eventSourceEntity = new EventSourceEntity(events);
    }
    public double getAverageRating() {
        return eventSourceEntity.getAverageRating();
    }

    public UUID getUuid() {
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
        private UUID uuid;

        public double getAverageRating() {
            if(ratings.size() == 0)
                return 0;
            return ratings.stream().reduce(0, (a, b) -> a+b) / ratings.size();
        }

        public UUID getUuid() {
            return uuid;
        }

        public EventSourceEntity(List<Event> events) {
            register(SessionCreated.class, this::apply);
            register(SessionRated.class, this::apply);
            //register

            events.forEach(this::apply);
        }

        protected int version;
        private Map<Class, Consumer> appliers = new HashMap<>();
        private <T extends Event> void register(Class<T> event, Consumer<T> consumer) {
            appliers.put(event, consumer);
        }

        private void apply(Event event) {
            Consumer applier = appliers.getOrDefault(event.getClass(), (e) -> { });
            applier.accept(event);
            version++;
        }

        private void apply(SessionCreated event) {
            this.uuid = event.uuid;
        }
        private void apply(SessionRated event) {
            this.ratings.add( event.stars.ordinal() );
        }

        public int getVersion() {
            return version;
        }
    }
}
