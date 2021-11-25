package de.malkusch.instanaassignment.model;

import static java.util.Objects.requireNonNull;

import java.time.Duration;

public final class CalculateLatencyService {

    public Latency calculate(Graph graph, Trace trace) throws NoSuchTraceException {
        requireNonNull(graph);
        requireNonNull(trace);

        // TODO Vectorize

        var adjancyMatrix = graph.adjacencyMatrix();
        var millis = 0;
        for (var i = 0; i < trace.services().size() - 1; i++) {
            var from = trace.services().get(i);
            var to = trace.services().get(i + 1);
            var weight = adjancyMatrix.element(graph.index(from), graph.index(to));
            millis += weight;
        }

        return new Latency(Duration.ofMillis(millis));
    }
}
