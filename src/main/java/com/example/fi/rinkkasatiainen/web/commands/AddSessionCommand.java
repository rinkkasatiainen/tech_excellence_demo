package com.example.fi.rinkkasatiainen.web.commands;

public class AddSessionCommand implements Command {
    public final String title;

    public AddSessionCommand(String title) {
        this.title = title;
    }
}
