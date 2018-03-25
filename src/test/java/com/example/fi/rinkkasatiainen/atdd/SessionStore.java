package com.example.fi.rinkkasatiainen.atdd;

import com.example.fi.rinkkasatiainen.web.participants.ParticipantsRoute;
import com.example.fi.rinkkasatiainen.web.session.commands.SessionsRoute;
import net.serenitybdd.screenplay.Actor;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionStore {
    public static Map<String, UUID> sessions = new HashMap<>();
    public static Map<String, UUID> users= new HashMap<>();


    public static <T> UUID getUUIDFromLocationHeader(ResponseEntity<T> sessionResponseEntity) {
        return sessionResponseEntity.getHeaders().get("location")
                .stream().map( str -> str.replaceAll(".*/", "") ).map(UUID::fromString).findFirst().get();
    }

    public static void storeNewSession(String title, ResponseEntity<SessionsRoute.NewSessionResponse> response) {
        sessions.put(title, getUUIDFromLocationHeader(response));
    }

    public static UUID withTitle(String title) {
        return sessions.getOrDefault(title, null);
    }

    public static <T extends Actor> UUID user(T name) {
        return users.getOrDefault(name.getName(), null);
    }

    public static <T extends Actor> void storeParticipant(T actor, ResponseEntity<ParticipantsRoute.ParticipantDTO> response) {
        users.put(actor.getName(), getUUIDFromLocationHeader(response));
    }
}
