package com.example.fi.rinkkasatiainen.web.queries;

import com.example.fi.rinkkasatiainen.model.Schedule;
import com.example.fi.rinkkasatiainen.web.model.Session;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionFeedbackRouteShould {

    public static final UUID UUID = java.util.UUID.randomUUID();
    private Schedule schedule;

    @Test
    public void create_feedback_with_no_feedback() throws Exception {
        schedule = mock(Schedule.class);
        SessionFeedbackRoute sessionFeedbackRoute = new SessionFeedbackRoute(schedule);

        SessionFeedbackResult feedback = mock(SessionFeedbackResult.class );
        when(schedule.findSessionFeeback(UUID)).thenReturn( feedback  );

        SessionFeedbackResult sessionFeedback = sessionFeedbackRoute.getSession(UUID.toString()).getBody();
        assertThat(sessionFeedback, equalTo( feedback ));
    }
}