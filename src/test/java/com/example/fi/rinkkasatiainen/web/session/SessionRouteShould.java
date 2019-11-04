package com.example.fi.rinkkasatiainen.web.session;

import com.example.fi.rinkkasatiainen.model.participants.ParticipantUUID;
import com.example.fi.rinkkasatiainen.model.session.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.Stars;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommand;
import com.example.fi.rinkkasatiainen.model.session.commands.RateSessionCommandHandler;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommand;
import com.example.fi.rinkkasatiainen.model.session.commands.RegisterParticipantCommandHandler;
import com.example.fi.rinkkasatiainen.web.session.commands.ParticipantDto;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionFeedback;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionRoute;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import static com.example.fi.rinkkasatiainen.web.session.SessionRouteShould.MatchingCommand.matchesToCommand;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SessionRouteShould {

    public static final SessionUUID UUID = SessionUUID.generate();
    private SessionRoute sessionRoute;
    private ParticipantDto participant;
    private RegisterParticipantCommandHandler registerParticipantCommandHandler;
    private RateSessionCommandHandler rateSessionCommandHandler;
    private ParticipantUUID participantUUID;

    @Before
    public void setUp() throws Exception {
        registerParticipantCommandHandler = mock(RegisterParticipantCommandHandler.class);
        rateSessionCommandHandler = mock(RateSessionCommandHandler.class);
        sessionRoute = new SessionRoute( registerParticipantCommandHandler, rateSessionCommandHandler );
        participantUUID = ParticipantUUID.generate();
        participant = new ParticipantDto(participantUUID.toString());
    }

    @Test
    public void register_participant_to_session() throws Exception {
        sessionRoute.register(UUID.toString(), participant);

        verify(registerParticipantCommandHandler).handles( argThat(matchesToCommand( new RegisterParticipantCommand(ParticipantDto.getUuid(participant), UUID))));
    }

    @Test
    public void rate_session() throws Exception {
        sessionRoute.rate(UUID.toString(), new SessionFeedback(5, participantUUID.toString()));

        verify(rateSessionCommandHandler).handles( new RateSessionCommand(UUID, Stars.FIVE, participantUUID));
    }


    public static class MatchingCommand implements ArgumentMatcher<RegisterParticipantCommand> {

        private final RegisterParticipantCommand expected;

        public MatchingCommand(RegisterParticipantCommand expected) {
            this.expected = expected;
        }

        @Override
        public boolean matches(RegisterParticipantCommand argument) {
            return argument.participantId.equals( expected.participantId);
        }

        public static MatchingCommand matchesToCommand(RegisterParticipantCommand expected){
            return new MatchingCommand(expected);
        }
    }
}