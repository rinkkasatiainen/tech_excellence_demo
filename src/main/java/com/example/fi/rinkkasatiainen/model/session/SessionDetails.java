package com.example.fi.rinkkasatiainen.model.session;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionDescriptionAdded;
import com.example.fi.rinkkasatiainen.util.Struct;

import java.util.List;
import java.util.UUID;

public class SessionDetails {


    private SessionDetails(List<Event> events){
        // Step 1: create EventSourceEntity - internal data structure
        // to hold the state
        // Load the history while doing it.
        eventSourceEntity = null;
    }








    public String getTitle() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    public SessionUUID getUuid() {
        return SessionUUID.from( UUID.randomUUID().toString() );
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
            //Step 2: register events
               // SessionCreated
               // SessionDescriptionAdded
            //Step 3: load the history.
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
