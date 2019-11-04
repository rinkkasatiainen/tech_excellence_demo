package com.example.fi.rinkkasatiainen.atdd.commands;

import com.example.fi.rinkkasatiainen.atdd.SessionStore;
import com.example.fi.rinkkasatiainen.atdd.Wiring;
import com.example.fi.rinkkasatiainen.model.session.Stars;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionFeedback;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionRoute;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;

import java.util.UUID;

public class RateASession implements Performable  {
    private final String title;
    private final Stars stars;

    private RateASession(String title) {
        this.title = title;
        this.stars = Stars.ZERO;
    }

    private RateASession(String title, Stars stars) {
        this.title = title;
        this.stars = stars;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        UUID uuid = SessionStore.withTitle(title);
        UUID userUUID = SessionStore.user(actor);
        SessionRoute sessionsRoute = new SessionRoute(Wiring.registerParticipantCommandHandler(), Wiring.rateSessionCommandHandler());

        sessionsRoute.rate( uuid.toString(), new SessionFeedback( stars.ordinal(), userUUID.toString()) );
    }

    public static RateASession withTitle(String title) {
        return new RateASession(title);
    }

    public RateASession as(Stars stars) {
        return new RateASession(this.title, stars);
    }
}
