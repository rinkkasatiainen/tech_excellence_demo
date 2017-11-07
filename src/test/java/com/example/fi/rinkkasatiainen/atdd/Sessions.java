package com.example.fi.rinkkasatiainen.atdd;

import com.example.fi.rinkkasatiainen.model.session.SessionDetails;
import com.example.fi.rinkkasatiainen.web.session.queries.AllSessionsRoute;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class Sessions implements Question<List<SessionDetails>> {

    public static Sessions allTalks() {
        return new Sessions();
    }

    @Override
    public List<SessionDetails> answeredBy(Actor actor) {
        AllSessionsRoute allSessionsRoute = new AllSessionsRoute(Wiring.schedule());

        ResponseEntity<List<SessionDetails>> listResponseEntity = allSessionsRoute.allSessions();
        return listResponseEntity.getBody();
    }
}
