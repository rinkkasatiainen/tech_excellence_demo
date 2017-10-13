package com.example.fi.rinkkasatiainen.web;

public interface Handler<M extends Message, R> {
    R handles(M any);
}
