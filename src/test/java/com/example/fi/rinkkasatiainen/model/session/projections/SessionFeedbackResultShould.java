package com.example.fi.rinkkasatiainen.model.session.projections;

import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.Stars;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import com.example.fi.rinkkasatiainen.model.session.events.SessionRated;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static com.example.fi.rinkkasatiainen.web.session.RegisterParticipantCommandHandlerShould.participantUUID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SessionFeedbackResultShould {


    public static final SessionUUID UUID = SessionUUID.generate();
    public static final ParticipantUUID participantUUId = ParticipantUUID.generate();

    @Test
    public void load_from_event_created() throws Exception {

        SessionFeedbackResult result = SessionFeedbackResult.load(Arrays.asList(new SessionCreated("TITLE", UUID)));

        assertThat(result.getUuid(), equalTo(UUID));
    }

    @Test
    public void empty_rating_for_participant_if_set_to_ZERO() throws Exception {
        SessionFeedbackResult result = SessionFeedbackResult.load(Arrays.asList(
                new SessionCreated("TITLE", UUID),
                new SessionRated(UUID, Stars.FIVE, participantUUID),
                new SessionRated(UUID, Stars.ZERO, participantUUID)
                ));

        assertThat(result.getAverageRating(), equalTo(0.0));
    }

    @Test
    public void can_rate_as_often_as_one_wants_and_only_last_one_counts() throws Exception {
        SessionFeedbackResult result = SessionFeedbackResult.load(Arrays.asList(
                new SessionCreated("TITLE", UUID),
                new SessionRated(UUID, Stars.FIVE, participantUUID),
                new SessionRated(UUID, Stars.ONE, participantUUID)
                ));

        assertThat(result.getAverageRating(), equalTo(1.0));
    }

    @Test
    public void calculates_multiple_participants() throws Exception {
        SessionFeedbackResult result = SessionFeedbackResult.load(Arrays.asList(
                new SessionCreated("TITLE", UUID),
                new SessionRated(UUID, Stars.FIVE, participantUUID),
                new SessionRated(UUID, Stars.ONE, ParticipantUUID.generate()),
                new SessionRated(UUID, Stars.FOUR, ParticipantUUID.generate()),
                new SessionRated(UUID, Stars.ONE, ParticipantUUID.generate())
                ));

        assertThat(result.getAverageRating(), equalTo(2.75));
    }
}

