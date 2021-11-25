package de.malkusch.instanaassignment.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.malkusch.instanaassignment.model.Graph.Factory.Edge;

public class EdgeTest {

    @Test
    public void shouldParseSingleDigitWeight() {
        var edge = Edge.parse("AB3");

        assertEquals(edge("A", "B", 3), edge);
    }

    @Test
    public void shouldParseSingleDoubleDigitWeight() {
        var edge = Edge.parse("AB31");

        assertEquals(edge("A", "B", 31), edge);
    }

    private static Edge edge(String left, String right, int weight) {
        return new Edge(new Service(left), new Service(right), weight);
    }
}
