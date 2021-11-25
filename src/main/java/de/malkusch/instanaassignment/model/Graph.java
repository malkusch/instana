package de.malkusch.instanaassignment.model;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;

import de.malkusch.instanaassignment.model.linalg.Vector;

public interface Graph {

    static abstract class Factory {
        static record Edge(Service left, Service right, int weight) {

            public static Edge parse(String edge) {
                if (edge.length() < 3) {
                    throw new IllegalArgumentException("Too short, must be something like AB3");
                }
                var left = new Service(edge.charAt(0));
                var right = new Service(edge.charAt(1));
                var weight = parseInt(edge.substring(2));
                return new Edge(left, right, weight);
            }
        }

        public abstract Graph build(Edge... edges);

        public final Graph parseCsv(String csv) {
            var edges = stream(csv.split(", *")).map(Edge::parse).toArray(Edge[]::new);
            return build(edges);
        }
    }

    Vector weightsForPath(Trace trace) throws NoSuchTraceException;

}
