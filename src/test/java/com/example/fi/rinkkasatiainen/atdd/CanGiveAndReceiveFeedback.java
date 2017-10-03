package com.example.fi.rinkkasatiainen.atdd;


import com.example.fi.rinkkasatiainen.Stars;
import com.example.fi.rinkkasatiainen.web.ParticipantsRoute;
import com.example.fi.rinkkasatiainen.web.SessionsRoute;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CanGiveAndReceiveFeedback {

    @Test
    public void can_create_user_and_give_feedback_to_session() throws Exception {
        SessionUUID session = given_a_session_exists_with_name();
        Participant participant = and_I_have_registered_as_participant();

        when_I(participant).rates_session(session).as( Stars.FIVE );

        then_session_should_have_average_rating_of( 5 );
    }

    private void then_session_should_have_average_rating_of(int expectedRating) {
        assertThat(true, equalTo(false));
    }

    private RateSession when_I(Participant participant) {
        RateSession rateSession = sessionUUID -> stars -> {
            System.out.println("rate sessionUUID based on");
        };
        return rateSession;
    }


    private Participant and_I_have_registered_as_participant() {
        ResponseEntity<Void> responseEntity = new ParticipantsRoute().create();
        String uuid = getUUIDFromLocationHeader(responseEntity);
        return new Participant(UUID.fromString(uuid));
    }


    private SessionUUID given_a_session_exists_with_name() {
        ResponseEntity<Void> sessionResponseEntity= new SessionsRoute().create();
        String uuid = getUUIDFromLocationHeader(sessionResponseEntity);
        return new SessionUUID(UUID.fromString(uuid));
    }

    private String getUUIDFromLocationHeader(ResponseEntity<Void> sessionResponseEntity) {
        return sessionResponseEntity.getHeaders().get("location")
                    .stream().map( str -> str.replaceAll(".*/", "") ).findFirst().get();
    }

    private class Participant {
        public final UUID uuid;

        public Participant(UUID uuid) {
            this.uuid = uuid;
        }
    }

}
