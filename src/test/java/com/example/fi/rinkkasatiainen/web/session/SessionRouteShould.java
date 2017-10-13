package com.example.fi.rinkkasatiainen.web.session;

import com.example.fi.rinkkasatiainen.web.participants.Participant;
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

    public static final UUID UUID = java.util.UUID.randomUUID();
    private SessionRoute sessionRoute;
    private Participant participant;
    private RegisterParticipantCommandHandler registerParticipantCommandHandler;

    @Before
    public void setUp() throws Exception {
        registerParticipantCommandHandler = mock(RegisterParticipantCommandHandler.class);
        sessionRoute = new SessionRoute( registerParticipantCommandHandler );
        participant = new Participant(UUID.randomUUID());
    }

    @Test
    public void register_participant_to_session() throws Exception {
        sessionRoute.register(UUID.toString(), participant);

        verify(registerParticipantCommandHandler).handles( argThat(matchesToCommand( new RegisterParticipantCommand(participant.uuid, UUID))));
    }

    public static class MatchingCommand extends TypeSafeMatcher<RegisterParticipantCommand>{

        private final RegisterParticipantCommand expected;

        public MatchingCommand(RegisterParticipantCommand expected) {
            this.expected = expected;
        }

        @Override
        protected boolean matchesSafely(RegisterParticipantCommand item) {
            return item.uuid.equals( expected.uuid );
        }

        @Override
        public void describeTo(Description description) {

        }

        public static MatchingCommand matchesToCommand(RegisterParticipantCommand expected){
            return new MatchingCommand(expected);
        }
    }
}