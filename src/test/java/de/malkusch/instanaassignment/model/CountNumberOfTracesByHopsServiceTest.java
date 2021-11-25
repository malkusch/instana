package de.malkusch.instanaassignment.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.malkusch.instanaassignment.model.CountNumberOfTracesByHopsService.Hops;

public class CountNumberOfTracesByHopsServiceTest {

    private final CountNumberOfTracesByHopsService countService = null; // TODO
    private final Graph graph = null; // TODO

    @Test
    public void countByMaxHopsShouldCount_Task6() {
        var start = new Service("C");
        var end = new Service("C");
        var max = new Hops(3);
        var count = countService.countByMaxHops(graph, max, start, end);

        assertEquals(2, count);
    }

    @Test
    public void countByHopsShouldCount_Task7() {
        var start = new Service("A");
        var end = new Service("C");
        var hops = new Hops(4);
        var count = countService.countByHops(graph, hops, start, end);

        assertEquals(3, count);
    }

}
