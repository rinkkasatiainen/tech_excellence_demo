package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.EventLoader;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionDescriptionAdded;
import com.example.fi.rinkkasatiainen.util.Struct;

import java.util.List;
import java.util.UUID;

public class SessionDetails {


    private SessionDetails(List<Event> history){
        // Step 1: create EventSourceEntity - internal data structure

        // to hold the state
        // Load the history while doing it.
        eventSourceEntity = new EventSourceEntity(history);
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

    public static SessionDetails load(List<Event> events) {
        return new SessionDetails(events);
    }








    private final EventSourceEntity eventSourceEntity;






    private class EventSourceEntity {
        private String title;
        private SessionUUID uuid;
        private String description;

        public EventSourceEntity(List<Event> events) {
            //Step 1: create EventLoader
            EventLoader eventLoader = new EventLoader();
            //Step 2: register events
               // SessionCreated
               // SessionDescriptionAdded
            eventLoader.register(SessionCreated.class, this::apply);
            eventLoader.register(SessionDescriptionAdded.class, this::apply);
            //Step 3: load the history.
            events.stream().forEach(eventLoader::apply);
        }














        private void apply(SessionCreated t) {
            this.title = t.title;
            this.uuid = t.uuid;
        }
        private void apply(SessionDescriptionAdded t) {
            this.description = t.description;
        }
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


}
