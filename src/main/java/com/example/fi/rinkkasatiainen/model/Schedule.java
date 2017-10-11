package com.example.fi.rinkkasatiainen.model;

import com.example.fi.rinkkasatiainen.web.model.Session;

import java.util.UUID;
import java.util.function.Supplier;

public class Schedule {
    private final Supplier<UUID> supplier;

    public Schedule(Supplier<UUID> supplier) {
        this.supplier = supplier;
    }

    public Session newSession() {
        return new Session(supplier.get());
    }

    public Session findSession(UUID uuid) {
        return null;
    }
}
