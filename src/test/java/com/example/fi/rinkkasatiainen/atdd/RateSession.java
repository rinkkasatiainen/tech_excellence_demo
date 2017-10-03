package com.example.fi.rinkkasatiainen.atdd;

@FunctionalInterface
public interface RateSession {
    Rate rates_session(SessionUUID sessionUUID);
}
