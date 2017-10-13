package com.example.fi.rinkkasatiainen.web.session;

import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommand;
import com.example.fi.rinkkasatiainen.model.session.commands.AddSessionCommandHandler;
import com.example.fi.rinkkasatiainen.web.session.commands.NewSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping(SessionsRoute.V1_SESSIONS)
public class SessionsRoute {
    public static final String V1_SESSIONS = "/v1/sessions";
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final AddSessionCommandHandler commandHandler;

    public SessionsRoute(AddSessionCommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<NewSessionResponse> create(@RequestBody NewSession newSession) {
        log.debug("POST /v1/sessions");

        SessionUUID uuid = commandHandler.handles(new AddSessionCommand(newSession.title));

        URI uri;
        try {
            uri = new URI(V1_SESSIONS + "/" + uuid.toString() );
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.created( uri ).body(new NewSessionResponse(uuid, newSession));
    }

    public class NewSessionResponse {
        public final UUID sessionId;
        public final String title;

        public NewSessionResponse(SessionUUID uuid, NewSession newSession) {
            this.sessionId = uuid.getId();
            this.title = newSession.title;
        }
    }
}
