package com.example.fi.rinkkasatiainen.web.session.commands;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewSession {
    public final String title;

    public NewSession(@JsonProperty("title") String title) {
         this.title = title;
    }
}
