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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SessionRoute.V1_SESSION)
public class SessionRoute {
    public static final String V1_SESSION = "/v1/session/{sessionId}";

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RegisterParticipantCommandHandler registerParticipantCommandHandler;
    private final RateSessionCommandHandler rateSessionCommandHandler;
    private Stars stars;

    public SessionRoute(RegisterParticipantCommandHandler registerParticipantCommandHandler, RateSessionCommandHandler rateSessionCommandHandler) {
        this.registerParticipantCommandHandler = registerParticipantCommandHandler;
        this.rateSessionCommandHandler = rateSessionCommandHandler;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Void> register(@PathVariable(value = "sessionId") String sessionId, @RequestBody Participant participant) {
        registerParticipantCommandHandler.handles( new RegisterParticipantCommand(participant.uuid, SessionUUID.from(sessionId)));

        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Void> rate(@PathVariable(value = "sessionId") String sessionId, @RequestBody SessionFeedback rating) {
        SessionUUID uuid = SessionUUID.from(sessionId);
        stars = Stars.from(rating.rating);
        rateSessionCommandHandler.handles(new RateSessionCommand(uuid, stars, rating.participant));

        return ResponseEntity.ok().build();
    }
}
