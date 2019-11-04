package com.example.fi.rinkkasatiainen.web.session.queries;

import com.example.fi.rinkkasatiainen.model.session.repositories.Schedule;
import com.example.fi.rinkkasatiainen.model.session.SessionDetails;
import com.example.fi.rinkkasatiainen.web.session.SessionRouteConstants;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SessionRouteConstants.V1_SESSIONS)
public class AllSessionsRoute {

    private final Schedule schedule;

    public AllSessionsRoute(Schedule schedule) {
        this.schedule = schedule;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(readOnly = true)
    public ResponseEntity<List<SessionDetails>> allSessions() {
        List<SessionDetails> details = schedule.findAllSessions();

        return ResponseEntity.ok(details);
    }

}
