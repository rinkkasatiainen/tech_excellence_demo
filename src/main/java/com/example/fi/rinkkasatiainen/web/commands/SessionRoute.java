package com.example.fi.rinkkasatiainen.web.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SessionRoute.V1_SESSION)
public class SessionRoute {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String V1_SESSION = "/v1/session/{sessionId}";

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Void> register(@PathVariable(value = "sessionId") String sessionId, @RequestBody Participant participant) {
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> rate(@PathVariable(value = "sessionId") String sessionId, @RequestBody SessionFeedback participant) {
        return ResponseEntity.ok().build();
    }
}
