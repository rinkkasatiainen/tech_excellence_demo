package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.web.model.Session;
import org.junit.Test;

import java.util.UUID;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ScheduleShould {


    private UUID random = UUID.randomUUID();
    private Supplier<UUID> randomUUIDSupplier = () -> random;

    @Test
    public void create_new_session() throws Exception {
        Schedule schedule = new Schedule(randomUUIDSupplier);

        Session session = schedule.newSession();

        assertThat( session.getUUID(), equalTo(random));
    }
}