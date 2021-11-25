package de.malkusch.instanaassignment.model;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class TraceTest {

    @Test
    public void shouldFailParsingSingle() {
        assertThrows(IllegalArgumentException.class, () -> Trace.parse("A"));
    }

    @Test
    public void shouldParsePair() {
        var trace = Trace.parse("A-B");

        assertEquals(trace("A", "B"), trace);
    }

    @Test
    public void shouldParseTriplet() {
        var trace = Trace.parse("A-B-C");

        assertEquals(trace("A", "B", "C"), trace);
    }

    private static Trace trace(String... services) {
        return new Trace(stream(services).map(Service::new).toList());
    }
}
