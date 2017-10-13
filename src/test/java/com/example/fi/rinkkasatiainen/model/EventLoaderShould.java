package com.example.fi.rinkkasatiainen.model;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class EventLoaderShould {

    private boolean called = false;

    @Test
    public void apply_registered_event() throws Exception {

        EventLoader loader = new EventLoader();
        loader.register(TestEvent.class, this::apply);

        loader.apply( new TestEvent() );

        assertThat(loader.getVersion(), equalTo(1));
        assertThat(called, equalTo(true));
    }

    @Test
    public void not_apply_unregistered_event_but_add_version() throws Exception {

        EventLoader loader = new EventLoader();
        loader.register(TestEvent.class, this::apply);

        loader.apply( new UnregisteredTestEvent() );

        assertThat(loader.getVersion(), equalTo(1));
        assertThat(called, equalTo(false));
    }

    @Test
    public void increment_version() throws Exception {

        EventLoader loader = new EventLoader();
        loader.register(TestEvent.class, this::apply);

        loader.apply( new UnregisteredTestEvent() );
        loader.apply( new TestEvent() );

        assertThat(loader.getVersion(), equalTo(2));
        assertThat(called, equalTo(true));
    }

    private void apply(TestEvent event) {
        called = true;
    }

    private class TestEvent implements Event{

    }

    private class UnregisteredTestEvent implements Event{

    }
}