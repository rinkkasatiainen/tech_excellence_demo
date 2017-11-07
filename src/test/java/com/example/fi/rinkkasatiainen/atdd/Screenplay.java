package com.example.fi.rinkkasatiainen.atdd;

import com.example.fi.rinkkasatiainen.model.session.SessionDetails;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.fi.rinkkasatiainen.atdd.Screenplay.SessionDescribedWith.sessionDescribedAs;
import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.CoreMatchers.hasItem;

@RunWith(SerenityRunner.class)
public class Screenplay {

    @Test
    public void screenplay() throws Exception {
        Actor adrian = Actor.named("Adrian");
        givenThat(adrian).wasAbleTo( CreateASession.with().title("title").and().description("desc"));

        when(adrian).attemptsTo(CreateASession.with().title("another").and().description("longer description") );

        then(adrian).should(seeThat( Sessions.allTalks(),
                hasItem( sessionDescribedAs("title", "desc") ),
                hasItem( sessionDescribedAs("another", "longer description") ) ));
    }

    static class SessionDescribedWith extends TypeSafeMatcher<SessionDetails> {

        private final String title;
        private final String description;

        private SessionDescribedWith(String title, String description) {
            this.title = title;
            this.description = description;
        }

        @Override
        protected boolean matchesSafely(SessionDetails item) {
            return title.equals(item.title) && description.equals(item.description);
        }

        @Override
        public void describeTo(Description description) {

        }

        public static SessionDescribedWith sessionDescribedAs(String title, String description ){
            return new SessionDescribedWith(title, description);
        }
    }
}
