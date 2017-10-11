package com.example.fi.rinkkasatiainen.web.queries;

import com.example.fi.rinkkasatiainen.model.Schedule;
import com.example.fi.rinkkasatiainen.web.commands.SessionRoute;
import com.example.fi.rinkkasatiainen.web.model.Session;
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
    SessionFeedbackQueryHandler queryHandler;
    public SessionFeedbackRoute(Schedule schedule) {
        queryHandler = new SessionFeedbackQueryHandler(schedule);
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<SessionFeedbackResult> getSession(@PathVariable(value = "session_id") String sessionId) {
        SessionFeedbackResult session = queryHandler.handles(new SessionFeedbackQuery(sessionId));

        return ResponseEntity.ok(session);
    }
}
