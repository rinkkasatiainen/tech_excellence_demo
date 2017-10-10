package com.example.fi.rinkkasatiainen.atdd;


import com.example.fi.rinkkasatiainen.Stars;
import com.example.fi.rinkkasatiainen.application.config.WebConfiguration;
import com.example.fi.rinkkasatiainen.model.Schedule;
import com.example.fi.rinkkasatiainen.web.commands.*;
import com.example.fi.rinkkasatiainen.web.queries.SessionFeedbackResult;
import com.example.fi.rinkkasatiainen.web.queries.SessionFeedbackRoute;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CanGiveAndReceiveFeedback {

    private WebConfiguration webConfiguration;

    @Before
    public void setUp() throws Exception {
        webConfiguration = new WebConfiguration();
    }

    @Test
    public void can_create_user_and_give_feedback_to_session() throws Exception {
        SessionUUID session = given_a_session();
        ParticipantUUID participant = and_I_have_registered_as_participant();

        when(participant).rates(session).as( Stars.FIVE );

        then_session(session).should_have_average_rating_of( 5.0 );
    }

    @Test
    public void can_reset_rating_by_giving_0() throws Exception {
        SessionUUID session = given_a_session();
        ParticipantUUID participant = and_I_have_registered_as_participant();

        when(participant).rates(session).as( Stars.FIVE );
        when(participant).rates(session).as( Stars.ZERO );

        then_session(session).should_have_average_rating_of( 0.0 );
    }

    private SessionResponse then_session(SessionUUID session) {
        return ( rating ) -> {
            ResponseEntity<SessionFeedbackResult> feedbackResult = new SessionFeedbackRoute().getSession(session.uuid.toString());
            SessionFeedbackResult body = feedbackResult.getBody();
            assertThat(body.averageRating, equalTo(rating));
        };
    }

    private RateSession when(ParticipantUUID participant) {
        RateSession rateSession = sessionUUID -> stars -> {
            new SessionRoute().register( sessionUUID.uuid.toString() , new Participant(participant.uuid));
            new SessionRoute().rate( sessionUUID.uuid.toString() , new SessionFeedback(stars.ordinal()));
        };
        return rateSession;
    }


    private ParticipantUUID and_I_have_registered_as_participant() {
        ResponseEntity<Void> responseEntity = new ParticipantsRoute().create();
        String uuid = getUUIDFromLocationHeader(responseEntity);
        return new ParticipantUUID(UUID.fromString(uuid));
    }


    private SessionUUID given_a_session() {
        ResponseEntity<Void> sessionResponseEntity= getSessionsRoute().create(new NewSession("title"));
        String uuid = getUUIDFromLocationHeader(sessionResponseEntity);
        return new SessionUUID(UUID.fromString(uuid));
    }

    private SessionsRoute getSessionsRoute() {

        return new SessionsRoute(webConfiguration.addSessionCommandHandler(getSchedule()));
    }

    private Schedule getSchedule() {
        return webConfiguration.schedule();
    }

    private String getUUIDFromLocationHeader(ResponseEntity<Void> sessionResponseEntity) {
        return sessionResponseEntity.getHeaders().get("location")
                .stream().map( str -> str.replaceAll(".*/", "") ).findFirst().get();
    }

    private class ParticipantUUID {
        public final UUID uuid;

        public ParticipantUUID(UUID uuid) {
            this.uuid = uuid;
        }
    }

    @FunctionalInterface
    public interface Rate {
        void as(Stars stars);
    }

    @FunctionalInterface
    public static interface RateSession {
        Rate rates(SessionUUID sessionUUID);
    }

    @FunctionalInterface
    public static interface SessionResponse {

        void should_have_average_rating_of(Double rating);
    }
}
