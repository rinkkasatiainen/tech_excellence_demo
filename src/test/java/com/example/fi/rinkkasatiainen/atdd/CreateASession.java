package com.example.fi.rinkkasatiainen.atdd;

import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import com.example.fi.rinkkasatiainen.web.session.commands.NewSession;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionsRoute;
import com.example.fi.rinkkasatiainen.web.session.queries.SessionFeedbackRoute;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreateASession implements Performable{
    private final String title;
    private final String description;

    private CreateASession(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        SessionsRoute sessionsRoute = new SessionsRoute(Wiring.addSessionCommandHandler());
        ResponseEntity<SessionsRoute.NewSessionResponse> response = sessionsRoute.create(new NewSession(title, description));

        assertThat( response.getStatusCodeValue(), equalTo(201));
    }

    public static CreateASession with() {
        return new CreateASession("", "");
    }

    public CreateASession title(String title) {
        return new CreateASession(title, this.description);
    }

    public CreateASession and() {
        return this;
    }

    public CreateASession description(String desc) {
        return new CreateASession(this.title, desc);
    }
}
