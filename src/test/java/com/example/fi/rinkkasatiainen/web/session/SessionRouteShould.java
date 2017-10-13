package com.example.fi.rinkkasatiainen.web.session;

import com.example.fi.rinkkasatiainen.model.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.Stars;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommand;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommand;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommandHandler;
import com.example.fi.rinkkasatiainen.web.participants.Participant;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionFeedback;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static com.example.fi.rinkkasatiainen.web.session.SessionRouteShould.MatchingCommand.matchesToCommand;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SessionRouteShould {

    public static final SessionUUID UUID = SessionUUID.generate();
    private SessionRoute sessionRoute;
    private Participant participant;
    private RegisterParticipantCommandHandler registerParticipantCommandHandler;
    private RateSessionCommandHandler rateSessionCommandHandler;

    @Before
    public void setUp() throws Exception {
        registerParticipantCommandHandler = mock(RegisterParticipantCommandHandler.class);
        rateSessionCommandHandler = mock(RateSessionCommandHandler.class);
        sessionRoute = new SessionRoute( registerParticipantCommandHandler, rateSessionCommandHandler );
        participant = new Participant(ParticipantUUID.generate());
    }

    @Test
    public void register_participant_to_session() throws Exception {
        sessionRoute.register(UUID.toString(), participant);

        verify(registerParticipantCommandHandler).handles( argThat(matchesToCommand( new RegisterParticipantCommand(participant.uuid, UUID))));
    }

    @Test
    public void rate_session() throws Exception {
        sessionRoute.rate(UUID.toString(), new SessionFeedback(5));

        verify(rateSessionCommandHandler).handles( new RateSessionCommand(UUID, Stars.FIVE));
    }

    public static class MatchingCommand extends TypeSafeMatcher<RegisterParticipantCommand>{

        private final RegisterParticipantCommand expected;

        public MatchingCommand(RegisterParticipantCommand expected) {
            this.expected = expected;
        }

        @Override
        protected boolean matchesSafely(RegisterParticipantCommand item) {
            return item.participantId.equals( expected.participantId);
        }

        @Override
        public void describeTo(Description description) {

        }

        public static MatchingCommand matchesToCommand(RegisterParticipantCommand expected){
            return new MatchingCommand(expected);
        }
    }
}