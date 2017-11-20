package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.EventLoader;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionDescriptionAdded;
import com.example.fi.rinkkasatiainen.util.Struct;

import java.util.List;

public class SessionDetails {

    private final EventSourceEntity eventSourceEntity;

    private SessionDetails(List<Event> events){
        eventSourceEntity = new EventSourceEntity(events);
    }

    public String getTitle() {
        return eventSourceEntity.title;
    }

    public String getDescription() {
        return eventSourceEntity.description;
    }

    public SessionUUID getUuid() {
        return eventSourceEntity.uuid;
    }

    @Override
    public boolean equals(Object o) {
        return new Struct.ForClass(eventSourceEntity).equals( ((SessionDetails) o).eventSourceEntity );
    }

    @Override
    public int hashCode() {
        return new Struct.ForClass(this.eventSourceEntity).hashCode();
    }

    @Override
    public String toString() {
        return new Struct.ForClass(this.eventSourceEntity).toString();
    }

    public static SessionDetails load(List<Event> events) {
        return new SessionDetails(events);
    }




    private class EventSourceEntity {
        private String title;
        private SessionUUID uuid;
        private String description;

        public EventSourceEntity(List<Event> events) {
            EventLoader loader = new EventLoader();
            loader.register(SessionCreated.class, this::apply);
            loader.register(SessionDescriptionAdded.class, this::apply);
            loader.load(events);
        }

        private void apply(SessionCreated t) {
            this.title = t.title;
            this.uuid = t.uuid;
        }
        private void apply(SessionDescriptionAdded t) {
            this.description = t.description;
        }
    }
}
