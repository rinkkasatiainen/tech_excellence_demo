package com.example.fi.rinkkasatiainen.model;

import java.util.List;
import java.util.UUID;

public interface EventStore {
    List<Event> findByUuid(UUID random);
}