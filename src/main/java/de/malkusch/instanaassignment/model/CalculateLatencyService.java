package de.malkusch.instanaassignment.model;

public interface CalculateLatencyService {

    Latency calculate(Graph graph, Trace trace) throws NoSuchTraceException;
}
