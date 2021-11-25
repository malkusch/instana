package de.malkusch.instanaassignment.model;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TraceTest {

    @Test
    public void shouldParseSingle() {
        var trace = Trace.parse("A");

        assertEquals(trace("A"), trace);
    }

    @Test
    public void shouldParsePair() {
        var trace = Trace.parse("A-B");

        assertEquals(trace("A", "B"), trace);
    }

    private static Trace trace(String... services) {
        return new Trace(stream(services).map(Service::new).toList());
    }
}
