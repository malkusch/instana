package de.malkusch.instanaassignment.model;

import static de.malkusch.instanaassignment.model.TestFixture.GRAPH;
import static de.malkusch.instanaassignment.model.TestFixture.GRAPH_FACTORY;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.malkusch.instanaassignment.infrastructure.DfsCountTracesByHopsService;

public class CountTracesWithinLatencyServiceTest {

    private final CountTracesWithinLatencyService countService = new DfsCountTracesByHopsService();

    @Test
    public void testCount_10() {
        var start = new Service("C");
        var end = new Service("C");
        var max = new Latency(Duration.ofMillis(30));

        var count = countService.count(GRAPH, max, start, end);

        assertEquals(7, count);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 4, 5, 6, 9 })
    public void shouldCountZeroWhenTracesTooFar(int millis) {
        var start = new Service("A");
        var end = new Service("C");
        var max = new Latency(Duration.ofMillis(millis));

        var count = countService.count(GRAPH, max, start, end);

        assertEquals(0, count);
    }

    @Test
    public void shouldCountZeroForUnreachableTrace() {
        var graph = GRAPH_FACTORY.parseCsv("AB5, BC5");
        var start = new Service("B");
        var end = new Service("A");
        var max = new Latency(Duration.ofMillis(100));

        var count = countService.count(graph, max, start, end);

        assertEquals(0, count);
    }

    @ParameterizedTest
    @ValueSource(ints = { 8, 11 })
    public void shouldCountOneTrace(int millis) {
        var start = new Service("A");
        var end = new Service("E");
        var max = new Latency(Duration.ofMillis(millis));

        var count = countService.count(GRAPH, max, start, end);

        assertEquals(1, count);
    }

    @ParameterizedTest
    @ValueSource(ints = { 12, 15 })
    public void shouldCountMoresTrace(int millis) {
        var graph = GRAPH_FACTORY.parseCsv("AB5, BC5, AC11, AD10, DC15");
        var start = new Service("A");
        var end = new Service("C");
        var max = new Latency(Duration.ofMillis(millis));

        var count = countService.count(graph, max, start, end);

        assertEquals(2, count);
    }

    @Test
    public void shouldCountCycle() {
        var graph = GRAPH_FACTORY.parseCsv("AB1, BC1, BA1");
        var start = new Service("A");
        var end = new Service("C");
        var max = new Latency(Duration.ofMillis(7));

        var count = countService.count(graph, max, start, end);

        assertEquals(3, count);
    }

}
