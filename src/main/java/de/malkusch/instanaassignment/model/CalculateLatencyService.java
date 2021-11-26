package de.malkusch.instanaassignment.model;

import de.malkusch.instanaassignment.model.graph.Graph;

public interface CalculateLatencyService {

    Latency calculate(Graph graph, Trace trace) throws NoSuchTraceException;
}
