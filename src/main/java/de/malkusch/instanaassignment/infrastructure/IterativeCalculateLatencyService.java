package de.malkusch.instanaassignment.infrastructure;

import static java.util.Objects.requireNonNull;

import de.malkusch.instanaassignment.model.CalculateLatencyService;
import de.malkusch.instanaassignment.model.Latency;
import de.malkusch.instanaassignment.model.NoSuchTraceException;
import de.malkusch.instanaassignment.model.Trace;
import de.malkusch.instanaassignment.model.graph.Graph;

public final class IterativeCalculateLatencyService implements CalculateLatencyService {

    @Override
    public Latency calculate(Graph graph, Trace trace) throws NoSuchTraceException {
        requireNonNull(graph);
        requireNonNull(trace);

        // TODO Vectorize

        var adjancyMatrix = graph.adjacencyMatrix();
        var sum = 0;
        for (var i = 0; i < trace.services().size() - 1; i++) {
            var from = trace.services().get(i);
            var to = trace.services().get(i + 1);
            var weight = adjancyMatrix.element(graph.index(from), graph.index(to));
            if (weight == 0) {
                throw new NoSuchTraceException("No trace between " + from + " and " + to);
            }
            sum += weight;
        }

        return Latency.fromWeight(sum);
    }
}
