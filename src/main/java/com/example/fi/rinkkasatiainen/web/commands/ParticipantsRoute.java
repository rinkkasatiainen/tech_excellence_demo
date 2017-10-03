package com.example.fi.rinkkasatiainen.web.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@RestController
@RequestMapping(ParticipantsRoute.V1_PARTICIPANTS)
public class ParticipantsRoute {
    public static final String V1_PARTICIPANTS = "/v1/participants";
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Void> create() {
        log.debug("POST /v1/participants");

        UUID uuid = UUID.randomUUID();
        URI uri;
        try {
            uri = new URI(V1_PARTICIPANTS + "/" + uuid.toString() );
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.created( uri ).build();
    }

}
