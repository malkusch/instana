package de.malkusch.instanaassignment.model;

import java.time.Duration;

public record Latency(Duration latency) {

    public Latency(Duration latency) {
        if (latency.compareTo(Duration.ZERO) <= 0) {
            throw new IllegalArgumentException("Must be positive");
        }
        this.latency = latency;
    }

}
