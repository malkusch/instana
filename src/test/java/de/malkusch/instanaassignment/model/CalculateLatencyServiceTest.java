package de.malkusch.instanaassignment.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class CalculateLatencyServiceTest {

    private final CalculateLatencyService calculateLatencyService = null; // TODO
    private final Graph graph = null; // TODO

    @Test
    public void shouldCalculate_1() throws NoSuchTraceException {
        var latency = calculateLatencyService.calculate(graph, Trace.parse("A-B-C"));

        assertEquals(latency(9), latency);
    }

    @Test
    public void shouldCalculate_2() throws NoSuchTraceException {
        var latency = calculateLatencyService.calculate(graph, Trace.parse("A-D"));

        assertEquals(latency(5), latency);
    }

    @Test
    public void shouldCalculate_3() throws NoSuchTraceException {
        var latency = calculateLatencyService.calculate(graph, Trace.parse("A-D-C"));

        assertEquals(latency(13), latency);
    }

    @Test
    public void shouldCalculate_4() throws NoSuchTraceException {
        var latency = calculateLatencyService.calculate(graph, Trace.parse("A-E-B-C-D"));

        assertEquals(latency(22), latency);
    }

    @Test
    public void shouldFailToCalculate_5() throws NoSuchTraceException {
        assertThrows(NoSuchTraceException.class,
                () -> calculateLatencyService.calculate(graph, Trace.parse("A-E-B-C-D")));
    }

    private static Latency latency(int latency) {
        return new Latency(Duration.ofMillis(latency));
    }
}
