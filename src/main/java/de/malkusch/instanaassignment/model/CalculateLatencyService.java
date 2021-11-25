package de.malkusch.instanaassignment.model;

import static java.util.Objects.requireNonNull;

import java.time.Duration;

import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;

public final class CalculateLatencyService {

    private final LinearAlgebra linearAlgebra;

    public CalculateLatencyService(LinearAlgebra linearAlgebra) {
        this.linearAlgebra = requireNonNull(linearAlgebra);
    }

    public Latency calculate(Graph graph, Trace trace) throws NoSuchTraceException {
        requireNonNull(graph);
        requireNonNull(trace);

        var weights = graph.weightsForPath(trace);
        var millis = linearAlgebra.sum(weights);
        return new Latency(Duration.ofMillis(millis));
    }
}
