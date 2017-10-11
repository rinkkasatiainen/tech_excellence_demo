package com.example.fi.rinkkasatiainen.web.queries;

import com.example.fi.rinkkasatiainen.web.commands.Query;

public class SessionFeedbackQuery implements Query{
    public final String sessionId;

    public SessionFeedbackQuery(String sessionId) {
        this.sessionId = sessionId;
    }
}
