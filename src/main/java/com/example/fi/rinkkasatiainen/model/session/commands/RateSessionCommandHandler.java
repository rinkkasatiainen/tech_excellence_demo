package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.schedule.Schedule;
import com.example.fi.rinkkasatiainen.web.CommandHandler;

public class RateSessionCommandHandler implements CommandHandler<RateSessionCommand>{
    public RateSessionCommandHandler(Schedule schedule) {

    }

    @Override
    public Void handles(RateSessionCommand any) {
        return null;
    }
}
