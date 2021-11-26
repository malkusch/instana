package de.malkusch.instanaassignment.model;

import static de.malkusch.instanaassignment.model.TestFixture.GRAPH;
import static de.malkusch.instanaassignment.model.TestFixture.LINEAR_ALGEBRA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.malkusch.instanaassignment.infrastructure.VectorizedCountNumberOfTracesByHopsService;
import de.malkusch.instanaassignment.model.CountNumberOfTracesByHopsService.Hops;

public class CountNumberOfTracesByHopsServiceTest {

    private final CountNumberOfTracesByHopsService countService = new VectorizedCountNumberOfTracesByHopsService(
            LINEAR_ALGEBRA);

    @Test
    public void countByMaxHopsShouldCount_Task6() {
        var start = new Service("C");
        var end = new Service("C");
        var max = new Hops(3);
        var count = countService.countByMaxHops(GRAPH, max, start, end);

        assertEquals(2, count);
    }

    @Test
    public void countByHopsShouldCount_Task7() {
        var start = new Service("A");
        var end = new Service("C");
        var hops = new Hops(4);
        var count = countService.countByHops(GRAPH, hops, start, end);

        assertEquals(3, count);
    }

}
