package com.example.fi.rinkkasatiainen.eventstore;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEventStore extends JpaRepository<StoredEvent, Long> {
    List<StoredEvent> findAllByLeastSignificantBitsAndMostSignificantBitsOrderByVersionAsc(long leastSignificantBits, long mostSignificantBits);
}
