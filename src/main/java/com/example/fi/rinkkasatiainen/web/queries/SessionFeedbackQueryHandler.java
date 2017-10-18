package com.example.fi.rinkkasatiainen.web.queries;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.model.session.projections.SessionFeedbackResult;
import com.example.fi.rinkkasatiainen.web.QueryHandler;

import java.util.ArrayList;

public class SessionFeedbackQueryHandler implements QueryHandler<SessionFeedbackQuery, SessionFeedbackResult> {
    private final Schedule schedule;

    public SessionFeedbackQueryHandler(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public SessionFeedbackResult handles(SessionFeedbackQuery feedbackQuery) {
        SessionFeedbackResult feedback = SessionFeedbackResult.load(new ArrayList<>());

        return feedback;
    }
}
