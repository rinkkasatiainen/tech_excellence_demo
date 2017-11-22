package com.example.fi.rinkkasatiainen.atdd.commands;

import com.example.fi.rinkkasatiainen.atdd.SessionStore;
import com.example.fi.rinkkasatiainen.web.participants.ParticipantsRoute;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterAsParticipant implements Performable{
    @Override
    public <T extends Actor> void performAs(T actor) {
        ParticipantsRoute participantsRoute = new ParticipantsRoute();
        ResponseEntity<ParticipantsRoute.ParticipantDTO> response = participantsRoute.create();

        SessionStore.storeParticipant(actor, response);
        assertThat( response.getStatusCodeValue(), equalTo(201));
    }

    public static Performable asNew() {
        return new RegisterAsParticipant();
    }
}
