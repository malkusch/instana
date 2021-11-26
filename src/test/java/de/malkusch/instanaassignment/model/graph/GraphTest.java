package de.malkusch.instanaassignment.model.graph;

import static de.malkusch.instanaassignment.model.TestFixture.GRAPH;
import static de.malkusch.instanaassignment.model.TestFixture.GRAPH_FACTORY;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.malkusch.instanaassignment.model.Service;

public class GraphTest {

    @Test
    public void testEdgesFromA() {
        var node = new Service("A");

        var edges = GRAPH.edges(node);

        assertEquals(edges("AB5", "AD5", "AE7"), edges);
    }

    @Test
    public void testEdgesFromB() {
        var node = new Service("B");

        var edges = GRAPH.edges(node);

        assertEquals(edges("BC4"), edges);
    }

    @Test
    public void leafShouldHaveNoNeighbors() {
        var graph = GRAPH_FACTORY.parseCsv("AB1, BC2");
        var leaf = new Service("C");

        var edges = graph.edges(leaf);

        assertTrue(edges.isEmpty());
    }

    private static Set<Edge> edges(String... edges) {
        return new HashSet<>(stream(edges).map(Edge::parse).toList());
    }
}
