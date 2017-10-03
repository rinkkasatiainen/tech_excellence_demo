package com.example.fi.rinkkasatiainen.web.commands;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionsRouteShould {

    @Test
    public void return_201_Created_for_creating_new_user_with_nonexisting_username() throws Exception {
        AddSessionCommandHandler commandHandler = mock(AddSessionCommandHandler.class);
        SessionsRoute route = new SessionsRoute( commandHandler );

        when( commandHandler.handles( any(AddSessionCommand.class) )).thenReturn( uuid );

        ResponseEntity<Void> responseEntity = route.create();

        assertThat( responseEntity.getHeaders().getLocation(), equalTo( new URI("/v1/sessions/" + uuid)));
    }

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String TWEET_ID = UUID.randomUUID().toString();
    private static final UUID uuid = UUID.randomUUID();

}
