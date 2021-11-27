package de.malkusch.instanaassignment.model;

import de.malkusch.instanaassignment.infrastructure.DijkstraFindShortestTraceService;
import de.malkusch.instanaassignment.infrastructure.VectorizedCalculateLatencyService;
import de.malkusch.instanaassignment.infrastructure.linalg.EjmlLinearAlgebra;
import de.malkusch.instanaassignment.model.graph.Graph;
import de.malkusch.instanaassignment.model.graph.Graph.Factory;
import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;

public class TestFixture {

    public static final LinearAlgebra LINEAR_ALGEBRA = new EjmlLinearAlgebra();
    public static final CalculateLatencyService CALCULATE_LATENCY_SERVICE = new VectorizedCalculateLatencyService(
            LINEAR_ALGEBRA);
    public static final FindShortestTraceService FIND_SHORTEST_TRACE_SERVICE = new DijkstraFindShortestTraceService(
            CALCULATE_LATENCY_SERVICE);
    public static final Factory GRAPH_FACTORY = new Factory(LINEAR_ALGEBRA);
    public static final Graph GRAPH = GRAPH_FACTORY.parseCsv("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");

}
