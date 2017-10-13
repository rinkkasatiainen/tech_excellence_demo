package com.example.fi.rinkkasatiainen.model.session.projections;

import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.session.events.SessionCreated;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class SessionFeedbackResultShould {


    public static final SessionUUID UUID = SessionUUID.generate();

    @Test
    public void load_from_event_created() throws Exception {

        SessionFeedbackResult result = SessionFeedbackResult.load(Arrays.asList(new SessionCreated("TITLE", UUID)));

        assertThat(result.getUuid(), equalTo(UUID));
    }
}