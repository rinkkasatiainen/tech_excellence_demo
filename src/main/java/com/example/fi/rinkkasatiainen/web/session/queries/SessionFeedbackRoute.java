package com.example.fi.rinkkasatiainen.web.session.queries;

import com.example.fi.rinkkasatiainen.model.Event;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import com.example.fi.rinkkasatiainen.web.queries.SessionFeedbackQuery;
import com.example.fi.rinkkasatiainen.web.queries.SessionFeedbackQueryHandler;
import com.example.fi.rinkkasatiainen.web.session.SessionRoute;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(SessionRoute.V1_SESSION)
public class SessionFeedbackRoute {
    SessionFeedbackQueryHandler queryHandler;
    public SessionFeedbackRoute(Schedule schedule) {
        queryHandler = new SessionFeedbackQueryHandler(schedule);
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<SessionFeedbackResult> getSession(@PathVariable(value = "sessionId") String sessionId) {

        SessionFeedbackResult session = createFake();

        return ResponseEntity.ok(session);
    }

    private SessionFeedbackResult createFake() {
        return SessionFeedbackResult.load(new ArrayList<>());
    }
}
