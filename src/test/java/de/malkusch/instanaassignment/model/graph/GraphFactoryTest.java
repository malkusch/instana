package de.malkusch.instanaassignment.model.graph;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import de.malkusch.instanaassignment.infrastructure.linalg.EjmlLinearAlgebra;

public class GraphFactoryTest {

    @ParameterizedTest
    @ValueSource(strings = { "AB5, BC4, CD8", "AB5,BC4,CD8", "AB5, BC4,CD8", "AB5,BC4, CD8", "AB5,BC4,CD8," })
    public void shouldParseGraph(String csv) {
        var factory = new Graph.Factory(new EjmlLinearAlgebra());
        var parsed = factory.parseCsv(csv);

        var expected = factory.build(edges("AB5", "BC4", "CD8"));
        assertEquals(expected, parsed);
    }

    @ParameterizedTest
    @ValueSource(strings = { "AAB5, BC4, CD8", "", "AB5,,BC4", ",AB5,BC4" })
    public void parseShouldFail(String csv) {
        var factory = new Graph.Factory(new EjmlLinearAlgebra());
        assertThrows(IllegalArgumentException.class, () -> factory.parseCsv(csv));
    }

    private static Edge[] edges(String... edges) {
        return stream(edges).map(Edge::parse).toArray(Edge[]::new);
    }
}
