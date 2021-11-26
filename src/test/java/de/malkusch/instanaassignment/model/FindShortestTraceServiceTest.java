package de.malkusch.instanaassignment.model;

import static de.malkusch.instanaassignment.model.TestFixture.CALCULATE_LATENCY_SERVICE;
import static de.malkusch.instanaassignment.model.TestFixture.FIND_SHORTEST_TRACE_SERVICE;
import static de.malkusch.instanaassignment.model.TestFixture.GRAPH;
import static de.malkusch.instanaassignment.model.TestFixture.GRAPH_FACTORY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import org.junit.jupiter.api.Test;

public class FindShortestTraceServiceTest {

    private final FindShortestTraceService findShortestTraceService = FIND_SHORTEST_TRACE_SERVICE;
    private final CalculateLatencyService calculateLatencyService = CALCULATE_LATENCY_SERVICE;

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

    @Test
    public void shouldNotFindShortestTrace() throws NoSuchTraceException {
        var start = new Service("C");
        var end = new Service("A");

        assertThrows(NoSuchTraceException.class, () -> findShortestTraceService.find(GRAPH, start, end));
    }

    @Test
    public void shouldNotFindShortestTraceWithSameStartAndEnd() throws NoSuchTraceException {
        var start = new Service("A");
        var end = new Service("A");

        assertThrows(NoSuchTraceException.class, () -> findShortestTraceService.find(GRAPH, start, end));
    }

    @Test
    public void shouldNotFindShortestTraceWhenStartIsALeaf() throws NoSuchTraceException {
        var graph = GRAPH_FACTORY.parseCsv("AB1, AC2");

        var start = new Service("B");
        var end = new Service("A");

        assertThrows(NoSuchTraceException.class, () -> findShortestTraceService.find(graph, start, end));
    }

    @Test
    public void shouldNotFindShortestTraceWhenStartIsALeafWithSameStartAndEnd() throws NoSuchTraceException {
        var graph = GRAPH_FACTORY.parseCsv("AB1, AC2");

        var start = new Service("B");
        var end = new Service("B");

        assertThrows(NoSuchTraceException.class, () -> findShortestTraceService.find(graph, start, end));
    }

    private static Latency latency(int latency) {
        return new Latency(Duration.ofMillis(latency));
    }
}
