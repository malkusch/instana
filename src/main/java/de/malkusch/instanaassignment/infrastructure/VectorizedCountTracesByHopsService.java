package de.malkusch.instanaassignment.infrastructure;

import static java.util.Objects.requireNonNull;

import de.malkusch.instanaassignment.model.CountTracesByHopsService;
import de.malkusch.instanaassignment.model.graph.Graph;
import de.malkusch.instanaassignment.model.Service;
import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;
import de.malkusch.instanaassignment.model.linalg.Matrix;

public final class VectorizedCountTracesByHopsService implements CountTracesByHopsService {

    private final LinearAlgebra linearAlgebra;

    public VectorizedCountTracesByHopsService(LinearAlgebra linearAlgebra) {
        this.linearAlgebra = requireNonNull(linearAlgebra);
    }

    @Override
    public int countByHops(Graph graph, Hops hops, Service start, Service end) {
        requireNonNull(graph);
        requireNonNull(hops);
        requireNonNull(start);
        requireNonNull(end);

        var adjacencyMatrix = graph.unweightedAdjacencyMatrix();
        var multiplied = dynamicPow(adjacencyMatrix, hops.count());
        var count = multiplied.element(graph.index(start), graph.index(end));
        return count;
    }

    private Matrix dynamicPow(Matrix matrix, int exponent) {
        // TODO This is an excellent opportunity for dynamic programming to improve the
        // performance
        return linearAlgebra.pow(matrix, exponent);
    }
}
