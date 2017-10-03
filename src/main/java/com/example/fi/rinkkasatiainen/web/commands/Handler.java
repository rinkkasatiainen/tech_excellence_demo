package com.example.fi.rinkkasatiainen.web.commands;

public interface Handler<M extends Message, R> {
    R handles(M any);
}
