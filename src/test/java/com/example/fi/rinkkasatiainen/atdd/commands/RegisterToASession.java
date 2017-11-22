package com.example.fi.rinkkasatiainen.atdd.commands;

import com.example.fi.rinkkasatiainen.atdd.SessionStore;
import com.example.fi.rinkkasatiainen.atdd.Wiring;
import com.example.fi.rinkkasatiainen.web.session.commands.ParticipantDto;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionRoute;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterToASession implements Performable{
    private final String title;

    private RegisterToASession(String title) {
        this.title = title;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        UUID uuid = SessionStore.withTitle(title);
        UUID participantUUID = SessionStore.user(actor);
        SessionRoute sessionRoute = new SessionRoute(Wiring.registerParticipantCommandHandler(), Wiring.rateSessionCommandHandler());

        ResponseEntity<Void> response = sessionRoute.register(uuid.toString(), new ParticipantDto(participantUUID.toString()));
        assertThat( response.getStatusCodeValue(), equalTo(200));
    }


    public static RegisterToASession withTitle(String title) {
        return new RegisterToASession(title);
    }
}
