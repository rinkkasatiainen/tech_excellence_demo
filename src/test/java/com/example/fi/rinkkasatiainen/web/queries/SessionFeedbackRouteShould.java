package com.example.fi.rinkkasatiainen.web.queries;

import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import com.example.fi.rinkkasatiainen.web.session.queries.SessionFeedbackRoute;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionFeedbackRouteShould {

    public static final SessionUUID UUID = SessionUUID.generate();
    private Schedule schedule;

    @Ignore
    @Test
    public void create_feedback_with_no_feedback() throws Exception {
        // TODO Step 2.0: organisers and speaker getting feedback for the session. Lists average rating (for now)
        schedule = mock(Schedule.class);  // Schedule is session Repository
        SessionFeedbackRoute sessionFeedbackRoute = new SessionFeedbackRoute(schedule);

        SessionFeedbackResult feedback = mock(SessionFeedbackResult.class );
        when(schedule.findSessionFeeback(UUID)).thenReturn( feedback  );

        SessionFeedbackResult sessionFeedback = sessionFeedbackRoute.getSession(UUID.toString()).getBody();
        assertThat(sessionFeedback, equalTo( feedback ));
    }
}