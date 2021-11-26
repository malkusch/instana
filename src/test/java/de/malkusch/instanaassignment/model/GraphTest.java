package de.malkusch.instanaassignment.model;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.malkusch.instanaassignment.model.Graph.Factory.Edge;

public class GraphTest {

    @Test
    public void testNeighborsFromA() {
        var start = new Service("A");

        var neighbors = TestFixture.GRAPH.neighbors(start);

        assertEquals(edges("AB5", "AD5", "AE7"), neighbors);
    }

    @Test
    public void testNeighborsFromB() {
        var start = new Service("B");

        var neighbors = TestFixture.GRAPH.neighbors(start);

        assertEquals(edges("BC4"), neighbors);
    }

    private static Set<Edge> edges(String... edges) {
        return new HashSet<>(stream(edges).map(Edge::parse).toList());
    }
}
