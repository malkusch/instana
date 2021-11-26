package de.malkusch.instanaassignment.model;

import static de.malkusch.instanaassignment.model.TestFixture.GRAPH;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import de.malkusch.instanaassignment.infrastructure.IterativeCalculateLatencyService;

public class FindShortestTraceServiceTest {

    private final FindShortestTraceService findShortestTraceService = null; // TODO
    private final CalculateLatencyService calculateLatencyService = new IterativeCalculateLatencyService();

    @Test
    public void shouldFindShortestBetweenAC_8() throws NoSuchTraceException {
        var start = new Service("A");
        var end = new Service("C");

        var shortest = findShortestTraceService.find(GRAPH, start, end);

        assertEquals(latency(9), calculateLatencyService.calculate(GRAPH, shortest));
    }

    @Test
    public void shouldFindShortestBetweenBB_9() throws NoSuchTraceException {
        var start = new Service("B");
        var end = new Service("B");

        var shortest = findShortestTraceService.find(GRAPH, start, end);

        assertEquals(latency(9), calculateLatencyService.calculate(GRAPH, shortest));
    }

    private static Latency latency(int latency) {
        return new Latency(Duration.ofMillis(latency));
    }
}
