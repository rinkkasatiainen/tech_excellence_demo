package com.example.fi.rinkkasatiainen.web.queries;

import com.example.fi.rinkkasatiainen.model.SessionUUID;
import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.Session;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import com.example.fi.rinkkasatiainen.web.QueryHandler;

import java.util.ArrayList;
import java.util.UUID;

public class SessionFeedbackQueryHandler implements QueryHandler<SessionFeedbackQuery, SessionFeedbackResult> {
    private final Schedule schedule;

    public SessionFeedbackQueryHandler(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public SessionFeedbackResult handles(SessionFeedbackQuery feedbackQuery) {
        SessionFeedbackResult feedback = SessionFeedbackResult.load(new ArrayList<>());
//        SessionFeedbackResult feedback = schedule.findSessionFeeback(SessionUUID.from(feedbackQuery.sessionId));

        return feedback;
    }
}
