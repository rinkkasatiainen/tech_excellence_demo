package com.example.fi.rinkkasatiainen.model.session.commands;

import com.example.fi.rinkkasatiainen.model.Stars;
import com.example.fi.rinkkasatiainen.util.Struct;
import com.example.fi.rinkkasatiainen.web.Command;

import java.util.UUID;

public class RateSessionCommand implements Command {

    public final UUID uuid;
    public final Stars stars;

    public RateSessionCommand(UUID uuid, Stars stars) {
        this.uuid = uuid;
        this.stars = stars;
    }

    @Override
    public boolean equals(Object o) {
        return new Struct.ForClass(this).equals( o );
    }

    @Override
    public int hashCode() {
        return new Struct.ForClass(this).hashCode();
    }

    @Override
    public String toString() {
        return new Struct.ForClass(this).toString();
    }
}
