package com.example.fi.rinkkasatiainen.atdd.queries;

import com.example.fi.rinkkasatiainen.atdd.SessionStore;
import com.example.fi.rinkkasatiainen.atdd.Wiring;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import com.example.fi.rinkkasatiainen.web.session.queries.SessionFeedbackRoute;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class SessionFeedback implements Question<SessionFeedbackResult> {


    private final String title;

    private SessionFeedback(String title) {
        this.title = title;
    }

    public static SessionFeedback withTitle(String title) {
        return new SessionFeedback(title);
    }

    @Override
    public SessionFeedbackResult answeredBy(Actor actor) {
        UUID uuid = SessionStore.withTitle(title);
        SessionFeedbackRoute sessionFeedbackRoute = new SessionFeedbackRoute(Wiring.schedule());

        ResponseEntity<SessionFeedbackResult> session = sessionFeedbackRoute.getSession(uuid.toString());

        return session.getBody();
    }
}
