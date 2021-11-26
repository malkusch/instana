package de.malkusch.instanaassignment.model;

import de.malkusch.instanaassignment.model.graph.Graph;

public interface FindShortestTraceService {

    Trace find(Graph graph, Service start, Service end) throws NoSuchTraceException;

}
