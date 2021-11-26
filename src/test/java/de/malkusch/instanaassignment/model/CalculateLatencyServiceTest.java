package de.malkusch.instanaassignment.model;

import static de.malkusch.instanaassignment.model.TestFixture.CALCULATE_LATENCY_SERVICE;
import static de.malkusch.instanaassignment.model.TestFixture.GRAPH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CalculateLatencyServiceTest {

    private final CalculateLatencyService calculateLatencyService = CALCULATE_LATENCY_SERVICE;

    @Test
    public void shouldCalculate_1() throws NoSuchTraceException {
        var latency = calculateLatencyService.calculate(GRAPH, Trace.parse("A-B-C"));

        assertEquals(Latency.fromWeight(9), latency);
    }

    @Test
    public void shouldCalculate_2() throws NoSuchTraceException {
        var latency = calculateLatencyService.calculate(GRAPH, Trace.parse("A-D"));

        assertEquals(Latency.fromWeight(5), latency);
    }

    @Test
    public void shouldCalculate_3() throws NoSuchTraceException {
        var latency = calculateLatencyService.calculate(GRAPH, Trace.parse("A-D-C"));

        assertEquals(Latency.fromWeight(13), latency);
    }

    @Test
    public void shouldCalculate_4() throws NoSuchTraceException {
        var latency = calculateLatencyService.calculate(GRAPH, Trace.parse("A-E-B-C-D"));

        assertEquals(Latency.fromWeight(22), latency);
    }

    @Test
    public void shouldFailToCalculate_5() throws NoSuchTraceException {
        assertThrows(NoSuchTraceException.class, () -> calculateLatencyService.calculate(GRAPH, Trace.parse("A-E-D")));
    }

    @Test
    public void shouldFailToCalculateWhenUnreachable() throws NoSuchTraceException {
        assertThrows(NoSuchTraceException.class, () -> calculateLatencyService.calculate(GRAPH, Trace.parse("A-C")));
    }

    @Test
    public void shouldFailToCalculateWhenWrongDirection() throws NoSuchTraceException {
        assertThrows(NoSuchTraceException.class, () -> calculateLatencyService.calculate(GRAPH, Trace.parse("E-A")));
    }
}
