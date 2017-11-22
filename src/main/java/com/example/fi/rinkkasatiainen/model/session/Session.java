package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.*;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommand;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionDescriptionAdded;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;
import com.example.fi.rinkkasatiainen.web.participants.Participant;

import java.util.*;
import java.util.function.Consumer;

public class Session implements AggregateRoot<SessionUUID> {
    private final EventSourceEntity eventSourceEntity;
    private final EventPublisher publisher;

    private Session() {
        this(new ArrayList<>());
    }

    private Session(List<Event> history) {
        eventSourceEntity = new EventSourceEntity(history);
        publisher = new EventPublisher(eventSourceEntity);
    }

    public static Session create(String title, SessionUUID uuid) {
        Session session = new Session();
        session.createSession(title, uuid);
        return session;
    }

    public static Session load(List<Event> events) {
        return new Session(events);
    }






    @Override
    public SessionUUID getUUID() {
        return eventSourceEntity.getUUID();
    }

    @Override
    public Integer getVersion() {
        return eventSourceEntity.getVersion();
    }

    @Override
    public List<Event> getUncommittedChanges() {
        return publisher.getUncommittedChanges();
    }

    @Override
    public void markChangesAsCommitted() {
        publisher.markChangesAsCommitted();
    }






    public void registerParticipant(ParticipantUUID participantUUid) {
        publisher.publish(new ParticipantRegisteredEvent(eventSourceEntity.uuid, participantUUid));
    }

    private void createSession(String title, SessionUUID uuid) {
        publisher.publish(new SessionCreated(title, uuid));
    }

    public void rate(RateSessionCommand command) {
        // Using a cool trick to change a query to a command. Instead of
        // if( isRegisterdParticipant ) - a query
        // onRegisteredParticipant (... Consumer) - provide a callback
        eventSourceEntity.onRegisteredParticipant(command.participantUUID, (participantUUID -> {
            publisher.publish(new SessionRated(command.stars, command.participantUUID));
        }));
    }

    public void setDescription(String description) {
        publisher.publish(new SessionDescriptionAdded(description, this.getUUID()));
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
        private SessionUUID uuid;
        private String title;
        private final EventLoader loader;
        private String description;
        private List<Stars> ratings;
        private List<ParticipantUUID> participants;

        public EventSourceEntity(List<Event> history) {
            this.ratings = new ArrayList<>();
            this.participants = new ArrayList<>();
            loader = new EventLoader();
            loader.register(SessionCreated.class, this::apply);
            loader.register(SessionRated.class, this::apply);
            loader.register(ParticipantRegisteredEvent.class, this::apply);
            loader.register(SessionDescriptionAdded.class, this::apply);
            history.forEach(loader::apply);
        }

        private void apply(SessionCreated sessionCreated) {
            this.uuid = sessionCreated.uuid;
            this.title = sessionCreated.title;
        }

        private void apply(ParticipantRegisteredEvent participantRegisteredEvent){
            this.participants.add(participantRegisteredEvent.participantUuid);
        }

        private void apply(SessionRated sessionRated){
            this.ratings.add(sessionRated.stars);
        }

        private void apply(SessionDescriptionAdded sessionDescriptionAdded){
            this.description = sessionDescriptionAdded.description;
        }

        public SessionUUID getUUID() {
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

        public void onRegisteredParticipant(ParticipantUUID participantUUID, Consumer<ParticipantUUID> consumer) {
            participants.stream().filter( p -> p.equals(participantUUID)).forEachOrdered( consumer );
        }
    }

}
