package de.malkusch.instanaassignment.model;

public interface FindShortestTraceService {

    Trace find(Graph graph, Service start, Service end) throws NoSuchTraceException;

}
