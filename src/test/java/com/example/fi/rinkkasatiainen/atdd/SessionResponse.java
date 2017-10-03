package com.example.fi.rinkkasatiainen.atdd;

@FunctionalInterface
public interface SessionResponse {

    void should_have_average_rating_of(Double rating);
}
