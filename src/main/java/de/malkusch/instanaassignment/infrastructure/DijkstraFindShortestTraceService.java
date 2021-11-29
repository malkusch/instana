package de.malkusch.instanaassignment.infrastructure;

import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import de.malkusch.instanaassignment.model.FindShortestTraceService;
import de.malkusch.instanaassignment.model.NoSuchTraceException;
import de.malkusch.instanaassignment.model.Service;
import de.malkusch.instanaassignment.model.Trace;
import de.malkusch.instanaassignment.model.graph.Edge;
import de.malkusch.instanaassignment.model.graph.Graph;
import de.malkusch.instanaassignment.model.graph.Graph.Factory;

public final class DijkstraFindShortestTraceService implements FindShortestTraceService {

    private static final int INFINITY = Integer.MAX_VALUE;
    private static final Service UNDEFINED = null;

    private final Factory graphFactory;

    public DijkstraFindShortestTraceService(Factory graphFactory) {
        this.graphFactory = requireNonNull(graphFactory);
    }

    private static final Service START = new Service("Start");

    @Override
    public Trace find(Graph graph, Service start, Service end) throws NoSuchTraceException {
        // Insert a helper node with identical edges as start, to support traces where
        // start and end are identical
        var helperStartEdges = graph.edges(start).stream().map(it -> new Edge(START, it.to(), it.weight())).toList();
        if (helperStartEdges.isEmpty()) {
            throw new NoSuchTraceException("No trace from " + start + " to " + end);
        }
        var edges = new ArrayList<>(helperStartEdges);
        edges.addAll(graph.edges());
        var graphWithStartNode = graphFactory.build(edges);
        if (graphWithStartNode.edges(START).isEmpty()) {
            throw new NoSuchTraceException("No trace from " + start + " to " + end);
        }

        var shortest = shortestPath(graphWithStartNode, START, end);
        return shortest.replaceFirst(start);
    }

    private Trace shortestPath(Graph graph, Service start, Service end) throws NoSuchTraceException {
        // Shamelessly implemented from Wikipedia's Pseudocode
        // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm#Pseudocode

        var q = new HashSet<Service>();
        var dist = new HashMap<Service, Integer>();
        var prev = new HashMap<Service, Service>();

        for (var service : graph.services()) {
            dist.put(service, INFINITY);
            prev.put(service, UNDEFINED);
            q.add(service);
        }
        dist.put(start, 0);

        while (!q.isEmpty()) {
            var u = q.stream().min(comparing(dist::get)).get();

            q.remove(u);

            for (var neighbor : graph.edges(u)) {
                var v = neighbor.to();

                if (!q.contains(v)) {
                    continue;
                }

                var alt = dist.get(u) + neighbor.weight().toWeight();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
                    prev.put(v, u);
                }
            }
        }

        var s = new LinkedList<Service>();
        var u = end;
        if (prev.get(u) == UNDEFINED) {
            throw new NoSuchTraceException("No trace from " + start + " to " + end);
        }
        while (u != UNDEFINED) {
            s.addFirst(u);
            u = prev.get(u);
        }

        return new Trace(s);
    }
}
