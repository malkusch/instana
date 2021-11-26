package de.malkusch.instanaassignment.infrastructure;

import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import de.malkusch.instanaassignment.model.CalculateLatencyService;
import de.malkusch.instanaassignment.model.FindShortestTraceService;
import de.malkusch.instanaassignment.model.Graph;
import de.malkusch.instanaassignment.model.NoSuchTraceException;
import de.malkusch.instanaassignment.model.Service;
import de.malkusch.instanaassignment.model.Trace;

public final class DijkstraFindShortestTraceService implements FindShortestTraceService {

    private static final int INFINITY = Integer.MAX_VALUE;
    private static final Service UNDEFINED = null;

    private final CalculateLatencyService calculateLatencyService;

    public DijkstraFindShortestTraceService(CalculateLatencyService calculateLatencyService) {
        this.calculateLatencyService = requireNonNull(calculateLatencyService);
    }

    @Override
    public Trace find(Graph graph, Service start, Service end) throws NoSuchTraceException {
        if (start.equals(end)) {
            var neighbors = graph.edges(start);
            var candidates = new ArrayList<Trace>();
            for (var neighbor : neighbors) {
                try {
                    var partialCandidate = shortestPath(graph, neighbor.to(), end);
                    var candidate = partialCandidate.prepend(start);
                    candidates.add(candidate);

                } catch (NoSuchTraceException e) {
                    continue;
                }
            }
            return shortest(graph, candidates);

        } else {
            return shortestPath(graph, start, end);
        }
    }

    private Trace shortest(Graph graph, Collection<Trace> traces) throws NoSuchTraceException {
        return traces.stream().min(Comparator.comparing(it -> calculateLatencyService.calculate(graph, it).latency()))
                .orElseThrow(() -> new NoSuchTraceException("No shortes trace"));
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

                var alt = dist.get(u) + neighbor.weight();
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
