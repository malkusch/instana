package de.malkusch.instanaassignment.model;

import java.time.Duration;

public record Latency(Duration value) {

    public Latency(Duration value) {
        if (value.compareTo(Duration.ZERO) <= 0) {
            throw new IllegalArgumentException("Must be positive");
        }
        this.value = value;
    }

    public static Latency fromWeight(int weight) {
        return new Latency(Duration.ofMillis(weight));
    }

    public int toWeight() {
        return (int) value.toMillis();
    }
}
