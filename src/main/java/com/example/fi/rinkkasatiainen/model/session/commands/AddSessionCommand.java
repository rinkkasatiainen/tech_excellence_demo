package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.web.Command;

public class AddSessionCommand implements Command {
    public final String title;

    public AddSessionCommand(String title) {
        this.title = title;
    }
}
