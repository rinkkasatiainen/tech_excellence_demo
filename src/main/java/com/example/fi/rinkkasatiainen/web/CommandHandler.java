package com.example.fi.rinkkasatiainen.web;

public interface CommandHandler<T extends Command> extends Handler<T, Void> {
}
