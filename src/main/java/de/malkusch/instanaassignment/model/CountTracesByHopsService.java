package de.malkusch.instanaassignment.model;

import static java.util.Objects.requireNonNull;

public interface CountTracesByHopsService {

    static record Hops(int count) {
        public Hops(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("Must be positive");
            }
            this.count = count;
        }
    }

    int countByHops(Graph graph, Hops hops, Service start, Service end);

    default int countByMaxHops(Graph graph, Hops max, Service start, Service end) {
        requireNonNull(graph);
        requireNonNull(max);
        requireNonNull(start);
        requireNonNull(end);

        var count = 0;
        for (int i = 1; i <= max.count; i++) {
            count += countByHops(graph, new Hops(i), start, end);
        }
        return count;
    }
}
