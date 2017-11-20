package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.web.Command;

public class AddSessionCommand implements Command {
    public final String title;
    public final String description;

    public AddSessionCommand(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
