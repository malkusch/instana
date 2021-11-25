package de.malkusch.instanaassignment.model;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;
import de.malkusch.instanaassignment.model.linalg.Matrix;

public record Graph(Matrix adjacencyMatrix, Matrix unweightedAdjacencyMatrix, Map<Service, Integer> serviceMap) {

    public static final class Factory {

        private final LinearAlgebra linearAlgebra;

        public Factory(LinearAlgebra linearAlgebra) {
            this.linearAlgebra = requireNonNull(linearAlgebra);
        }

        static record Edge(Service from, Service to, int weight) {

            public Edge(Service from, Service to, int weight) {
                this.from = requireNonNull(from);

                if (from.equals(to)) {
                    throw new IllegalArgumentException("Edge must not point to itself");
                }
                this.to = requireNonNull(to);

                if (weight <= 0) {
                    throw new IllegalArgumentException("weight must be positive");
                }
                this.weight = weight;
            }

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

        public Graph build(Edge... edges) {
            var serviceMap = new HashMap<Service, Integer>();
            var distinctServices = new HashSet<>(stream(edges).flatMap(it -> Stream.of(it.from, it.to)).toList());
            var index = 1;
            for (var service : distinctServices) {
                serviceMap.put(service, index);
                index++;
            }

            var adjacencyMatrix = linearAlgebra.matrix(distinctServices.size(), distinctServices.size());
            var unweightedAdjacencyMatrix = linearAlgebra.matrix(distinctServices.size(), distinctServices.size());
            for (var edge : edges) {
                var i = serviceMap.get(edge.from);
                var j = serviceMap.get(edge.to);
                adjacencyMatrix.set(i, j, edge.weight);
                unweightedAdjacencyMatrix.set(i, j, 1);
            }

            return new Graph(adjacencyMatrix, unweightedAdjacencyMatrix, serviceMap);
        }

        public Graph parseCsv(String csv) {
            var edges = stream(csv.split(", *")).map(Edge::parse).toArray(Edge[]::new);
            return build(edges);
        }
    }

    public int index(Service service) {
        return serviceMap.get(service);
    }
}
