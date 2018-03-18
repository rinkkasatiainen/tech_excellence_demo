package com.example.fi.rinkkasatiainen.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventLoader {

    protected int version;
    private Map<Class, Consumer> appliers = new HashMap<>();

    public <T extends Event> void register(Class<T> event, Consumer<T> consumer) {
        appliers.put(event, consumer);
    }

    public void apply(Event event) {
        Consumer applier = appliers.getOrDefault(event.getClass(), (e) -> { });
        applier.accept(event);
        version++;
    }

    public int getVersion() {
        return version;
    }

    public void load(List<Event> events) {
        events.stream().forEachOrdered(this::apply);
    }
}
