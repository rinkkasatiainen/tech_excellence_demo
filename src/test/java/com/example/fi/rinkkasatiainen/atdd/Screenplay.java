package com.example.fi.rinkkasatiainen.atdd;

import com.example.fi.rinkkasatiainen.atdd.commands.CreateASession;
import com.example.fi.rinkkasatiainen.atdd.commands.RateASession;
import com.example.fi.rinkkasatiainen.atdd.commands.RegisterAsParticipant;
import com.example.fi.rinkkasatiainen.atdd.commands.RegisterToASession;
import com.example.fi.rinkkasatiainen.atdd.queries.SessionFeedback;
import com.example.fi.rinkkasatiainen.atdd.queries.Sessions;
import com.example.fi.rinkkasatiainen.model.Stars;
import com.example.fi.rinkkasatiainen.model.session.SessionDetails;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.example.fi.rinkkasatiainen.atdd.Screenplay.SessionDescribedWith.withTitleAndDescriptionOf;
import static com.example.fi.rinkkasatiainen.atdd.Screenplay.SessionFeedbackMatcher.hasAverageRatingOf;
import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.CoreMatchers.hasItem;

@RunWith(SerenityRunner.class)
public class Screenplay {

    @Test
    public void shouldSeeListOfTalks() throws Exception {
        Actor aki = Actor.named("Aki");
        givenThat(aki).wasAbleTo( CreateASession.with().title("title").and().description("desc"));

        when(aki).attemptsTo(CreateASession.with().title("another").and().description("longer description") );

        then(aki).should(seeThat( Sessions.allTalks(),
                hasItem( withTitleAndDescriptionOf("title", "desc") ),
                hasItem( withTitleAndDescriptionOf("another", "longer description") ) ));
    }


    @Test
    public void shouldGetFeedbackOfASession() throws Exception {
        Actor aki = Actor.named("Aki");
        givenThat(aki).wasAbleTo( CreateASession.with().title("title").and().description("desc"));

        Actor rachel = Actor.named("Rachel");
        givenThat(rachel).wasAbleTo(RegisterAsParticipant.asNew());
        givenThat(rachel).wasAbleTo(RegisterToASession.withTitle("title"));

        when(rachel).attemptsTo(RateASession.withTitle("title").as(Stars.FIVE));

        then(aki).should(seeThat(SessionFeedback.withTitle("title"),
                hasAverageRatingOf(5.0) ));
    }







    static class SessionFeedbackMatcher extends TypeSafeMatcher<SessionFeedbackResult> {

        private final double averageRating;

        private SessionFeedbackMatcher(double averageRating) {
            this.averageRating = averageRating;
        }

        @Override
        protected boolean matchesSafely(SessionFeedbackResult item) {
            return item.getAverageRating() == averageRating;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Feedback with average score of " + averageRating );
        }

        static SessionFeedbackMatcher hasAverageRatingOf(double averageRating){
            return new SessionFeedbackMatcher(averageRating);
        }
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
            return title.equals(item.getTitle()) && description.equals(item.getDescription());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Session with title '" + title + "' and descrpition '" + this.description + "'");
        }

        public static SessionDescribedWith withTitleAndDescriptionOf(String title, String description ){
            return new SessionDescribedWith(title, description);
        }
    }



}

