package de.malkusch.instanaassignment.model;

import static java.util.Objects.requireNonNull;

import java.util.Set;

public interface CountTracesWithinLatencyService {

    Set<Trace> search(Graph graph, Latency max, Service start, Service end);

    default int count(Graph graph, Latency max, Service start, Service end) {
        requireNonNull(graph);
        requireNonNull(max);
        requireNonNull(start);
        requireNonNull(end);

        return search(graph, max, start, end).size();
    }

}
