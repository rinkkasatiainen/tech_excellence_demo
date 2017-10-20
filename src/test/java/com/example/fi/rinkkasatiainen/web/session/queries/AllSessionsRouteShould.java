package com.example.fi.rinkkasatiainen.web.session.queries;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.SessionDetails;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AllSessionsRouteShould {


    private Schedule schedule;

    @Before
    public void setUp() throws Exception {
        schedule = mock(Schedule.class);
    }

    @Test
    public void return_a_list_of_sessions_details() throws Exception {
        AllSessionsRoute route = new AllSessionsRoute(schedule);

        SessionDetails sessionDetails = mock(SessionDetails.class);
        when(schedule.findAllSessions()).thenReturn(Arrays.asList(sessionDetails));

        ResponseEntity<List<SessionDetails>> responseEntity = route.allSessions();

        assertThat(responseEntity.getBody(), hasItem(sessionDetails));


    }
}