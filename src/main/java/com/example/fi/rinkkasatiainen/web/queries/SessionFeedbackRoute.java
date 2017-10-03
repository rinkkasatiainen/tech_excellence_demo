package com.example.fi.rinkkasatiainen.web.queries;

import com.example.fi.rinkkasatiainen.web.commands.SessionRoute;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SessionRoute.V1_SESSION)
public class SessionFeedbackRoute {

    @RequestMapping(value = "/feedback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<SessionFeedbackResult> getSession(@PathVariable(value = "session_id") String sessionId) {
        return ResponseEntity.ok( new SessionFeedbackResult(0.0) );
    }
}
