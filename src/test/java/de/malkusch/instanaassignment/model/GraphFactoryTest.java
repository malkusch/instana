package de.malkusch.instanaassignment.model;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.malkusch.instanaassignment.infrastructure.linalg.JblasLinearAlgebra;
import de.malkusch.instanaassignment.model.Graph.Factory.Edge;

public class GraphFactoryTest {

    @ParameterizedTest
    @ValueSource(strings = { "AB5, BC4, CD8", "AB5,BC4,CD8", "AB5, BC4,CD8", "AB5,BC4, CD8" })
    public void shouldParseGraph(String csv) {
        var factory = new Graph.Factory(new JblasLinearAlgebra());
        var parsed = factory.parseCsv(csv);

        var expected = factory.build(edges("AB5", "BC4", "CD8"));
        assertEquals(expected, parsed);
    }

    private static Edge[] edges(String... edges) {
        return stream(edges).map(Edge::parse).toArray(Edge[]::new);
    }
}
