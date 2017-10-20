package com.example.fi.rinkkasatiainen.atdd;


import com.example.fi.rinkkasatiainen.FakeEventStore;
import com.example.fi.rinkkasatiainen.model.*;
import com.example.fi.rinkkasatiainen.application.config.WebConfiguration;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import com.example.fi.rinkkasatiainen.web.participants.ParticipantsRoute;
import com.example.fi.rinkkasatiainen.web.session.commands.NewSession;
import com.example.fi.rinkkasatiainen.web.session.commands.ParticipantDto;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionFeedback;
import com.example.fi.rinkkasatiainen.web.session.queries.SessionFeedbackRoute;
import com.example.fi.rinkkasatiainen.web.session.SessionRoute;
import com.example.fi.rinkkasatiainen.web.session.SessionsRoute;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CanGiveAndReceiveFeedback {

    private WebConfiguration webConfiguration;
    private EventStore eventStore;

    @Before
    public void setUp() throws Exception {
        webConfiguration = new WebConfiguration();
        eventStore = new FakeEventStore();
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
            ResponseEntity<SessionFeedbackResult> feedbackResult = new SessionFeedbackRoute(getSchedule()).getSession(session.getId().toString());
            SessionFeedbackResult body = feedbackResult.getBody();
            assertThat(body.getAverageRating(), equalTo(rating));
        };
    }

    private RateSession when(ParticipantUUID participant) {
        RateSession rateSession = sessionUUID -> stars -> {
            new SessionRoute(
                    getRegisterParticipantCommandHandler(),
                    getRateSessionCommandHandler()
            ).register( sessionUUID.getId().toString() , new ParticipantDto(participant.toString()));
            new SessionRoute(
                    getRegisterParticipantCommandHandler(),
                    getRateSessionCommandHandler()
            ).rate( sessionUUID.getId().toString(), new SessionFeedback(stars.ordinal(), participant.toString()));
        };
        return rateSession;
    }

    private RateSessionCommandHandler getRateSessionCommandHandler() {
        return webConfiguration.rateSessionCommandHandler(getSchedule(), getEventPublisher());
    }

    private RegisterParticipantCommandHandler getRegisterParticipantCommandHandler() {
        return webConfiguration.registerParticipantCommandHandler(getSchedule(), getAudience(), getEventPublisher());
    }

    private EventPublisher getEventPublisher() {
        return webConfiguration.getEventPublisher(eventStore);
    }

    private Audience getAudience() {
        return webConfiguration.audience(eventStore);
    }

    private Schedule getSchedule() {
        return webConfiguration.schedule( eventStore );
    }


    private ParticipantUUID and_I_have_registered_as_participant() {
        ResponseEntity<ParticipantsRoute.ParticipantDTO> responseEntity = new ParticipantsRoute().create();
        String uuid = getUUIDFromLocationHeader(responseEntity);
        return ParticipantUUID.from(uuid);
    }


    private SessionUUID given_a_session() {
        ResponseEntity<SessionsRoute.NewSessionResponse> sessionResponseEntity= getSessionsRoute().create(new NewSession("title"));
        String uuid = getUUIDFromLocationHeader(sessionResponseEntity);
        return SessionUUID.from(uuid);
    }

    private SessionsRoute getSessionsRoute() {

        return new SessionsRoute(webConfiguration.addSessionCommandHandler(getSchedule(), getEventPublisher()));
    }

    private <T> String getUUIDFromLocationHeader(ResponseEntity<T> sessionResponseEntity) {
        return sessionResponseEntity.getHeaders().get("location")
                .stream().map( str -> str.replaceAll(".*/", "") ).findFirst().get();
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
