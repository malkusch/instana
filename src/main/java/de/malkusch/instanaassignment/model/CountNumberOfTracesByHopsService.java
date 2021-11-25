package de.malkusch.instanaassignment.model;

import static java.util.Objects.requireNonNull;

import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;
import de.malkusch.instanaassignment.model.linalg.Matrix;

public final class CountNumberOfTracesByHopsService {

    private final LinearAlgebra linearAlgebra;

    public CountNumberOfTracesByHopsService(LinearAlgebra linearAlgebra) {
        this.linearAlgebra = requireNonNull(linearAlgebra);
    }

    static record Hops(int count) {
        public Hops(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("Must be positive");
            }
            this.count = count;
        }
    }

    public int countByHops(Graph graph, Hops hops, Service start, Service end) {
        requireNonNull(graph);
        requireNonNull(hops);
        requireNonNull(start);
        requireNonNull(end);

        var adjacencyMatrix = graph.unweightedAdjacencyMatrix();
        var multiplied = dynamicPow(adjacencyMatrix, hops.count);
        var count = multiplied.element(graph.index(start), graph.index(end));
        return count;
    }

    public int countByMaxHops(Graph graph, Hops max, Service start, Service end) {
        requireNonNull(graph);
        requireNonNull(max);
        requireNonNull(start);
        requireNonNull(end);

        var count = 0;
        for (int i = 1; i <= max.count; i++) {
            count += countByHops(graph, new Hops(i), start, end);
        }
        return count;
    }

    private Matrix dynamicPow(Matrix matrix, int exponent) {
        // TODO This is an excellent opportunity for dynamic programming to improve the
        // performance
        return linearAlgebra.pow(matrix, exponent);
    }
}
