package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.EventLoader;
import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.Stars;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;

import java.util.*;

public class Session {
    private final EventSourceEntity eventSourceEntity;
    private final EventPublisher publisher;

    private Session() {
        this(new ArrayList<>());
    }

    public UUID getUUID() {
        return eventSourceEntity.getUUID();
    }

    private Session(List<Event> history) {
        eventSourceEntity = new EventSourceEntity(history);
        publisher = new EventPublisher(eventSourceEntity);
    }

    public void registerParticipant(ParticipantUUID participantUUid) {
        publisher.publish(new ParticipantRegisteredEvent(eventSourceEntity.uuid, participantUUid));
    }

    public Integer getVersion() {
        return eventSourceEntity.getVersion();
    }

    public List<Event> getUncommittedChanges() {
        return publisher.getUncommittedChanges();
    }

    public void markChangesAsCommitted() {
        publisher.markChangesAsCommitted();
    }

    public static Session create(String title, UUID uuid) {
        Session session = new Session();
        session.createSession(title, uuid);
        return session;
    }

    private void createSession(String title, UUID uuid) {
        publisher.publish(new SessionCreated(title, uuid));
    }

    public static Session load(List<Event> events) {
        return new Session(events);
    }

    public static Session load(Event... events) {
        return new Session(Arrays.asList(events));
    }

    public void rate(Stars stars) {
        publisher.publish(new SessionRated(this.getUUID(), stars));
    }

    private class EventPublisher{
        private List<Event> uncommittedChanges;
        private final EventSourceEntity eventSourceEntity;

        public EventPublisher(EventSourceEntity eventSourceEntity) {
            this.eventSourceEntity = eventSourceEntity;
            this.uncommittedChanges = new ArrayList<>();
        }

        private void publish(Event event) {
            // send to event listeners
            eventSourceEntity.apply( event );
            uncommittedChanges.add( event );
        }

        public void markChangesAsCommitted() {
            this.uncommittedChanges.clear();
        }

        public List<Event> getUncommittedChanges() {
            return new ArrayList<>(Collections.unmodifiableList( uncommittedChanges ));
        }
    }

    private class EventSourceEntity{
        private java.util.UUID uuid;
        private String title;
        private final EventLoader loader;
        private List<Stars> ratings;

        public EventSourceEntity(List<Event> history) {
            this.ratings = new ArrayList<>();
            loader = new EventLoader();
            loader.register(SessionCreated.class, this::apply);

            history.forEach(loader::apply);
        }

        private void apply(SessionCreated sessionCreated) {
            this.uuid = sessionCreated.uuid;
            this.title = sessionCreated.title;
        }

        private void apply(SessionRated sessionRated){
            this.ratings.add(sessionRated.stars);
        }

        public UUID getUUID() {
            return uuid;
        }

        public Integer getVersion() {
            return loader.getVersion();
        }

        public String getTitle() {
            return title;
        }

        protected void apply(Event event) {
            loader.apply(event);
        }
    }

}
