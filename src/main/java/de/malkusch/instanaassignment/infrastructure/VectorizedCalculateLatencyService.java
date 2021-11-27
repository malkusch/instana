package de.malkusch.instanaassignment.infrastructure;

import static java.util.Objects.requireNonNull;

import de.malkusch.instanaassignment.model.CalculateLatencyService;
import de.malkusch.instanaassignment.model.Latency;
import de.malkusch.instanaassignment.model.NoSuchTraceException;
import de.malkusch.instanaassignment.model.Trace;
import de.malkusch.instanaassignment.model.graph.Graph;
import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;
import de.malkusch.instanaassignment.model.linalg.Matrix;

public final class VectorizedCalculateLatencyService implements CalculateLatencyService {

    private final LinearAlgebra linearAlgebra;

    public VectorizedCalculateLatencyService(LinearAlgebra linearAlgebra) {
        this.linearAlgebra = requireNonNull(linearAlgebra);
    }

    @Override
    public Latency calculate(Graph graph, Trace trace) throws NoSuchTraceException {
        var adjacencyMatrix = graph.adjacencyMatrix();
        var vectorizedTrace = vectorize(graph, trace);

        var result = linearAlgebra.multiply(adjacencyMatrix, vectorizedTrace);
        var sum = linearAlgebra.trace(result);
        if (sum == 0) {
            throw new NoSuchTraceException(trace.toString());
        }
        return Latency.fromWeight(sum);
    }

    private Matrix vectorize(Graph graph, Trace trace) {
        var matrix = linearAlgebra.matrix(graph.adjacencyMatrix().dimensions());
        for (var i = 0; i < trace.services().size() - 1; i++) {
            var from = graph.index(trace.services().get(i));
            var to = graph.index(trace.services().get(i + 1));
            matrix.set(to, from, 1);
        }
        return matrix;
    }
}
