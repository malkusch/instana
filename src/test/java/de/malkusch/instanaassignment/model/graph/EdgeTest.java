package de.malkusch.instanaassignment.model.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.malkusch.instanaassignment.model.Service;

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

    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  1", "A", "A3", "AB", "3", "ABC", "ABC3", "AA3", "AB0", "AB-1", "AB1A",
            " AB1" })
    public void parseShouldFail(String edge) {
        assertThrows(IllegalArgumentException.class, () -> Edge.parse(edge));
    }

    private static Edge edge(String left, String right, int weight) {
        return new Edge(new Service(left), new Service(right), weight);
    }
}
