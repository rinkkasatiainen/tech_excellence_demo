package com.example.fi.rinkkasatiainen.web.queries;

import com.example.fi.rinkkasatiainen.model.Schedule;
import com.example.fi.rinkkasatiainen.web.commands.SessionFeedback;
import com.example.fi.rinkkasatiainen.web.model.Session;

import java.util.UUID;

public class SessionFeedbackQueryHandler implements QueryHandler<SessionFeedbackQuery, SessionFeedbackResult>{
    private final Schedule schedule;

    public SessionFeedbackQueryHandler(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public SessionFeedbackResult handles(SessionFeedbackQuery feedbackQuery) {
        Session session = schedule.findSession(UUID.fromString(feedbackQuery.sessionId));
        SessionFeedbackResult feedback = schedule.findSessionFeeback(UUID.fromString(feedbackQuery.sessionId));

        return feedback;
    }
}
