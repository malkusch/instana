package de.malkusch.instanaassignment.model.graph;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import de.malkusch.instanaassignment.model.Service;
import de.malkusch.instanaassignment.model.linalg.Dimensions;
import de.malkusch.instanaassignment.model.linalg.LinearAlgebra;
import de.malkusch.instanaassignment.model.linalg.Matrix;

public record Graph(Matrix adjacencyMatrix, Matrix unweightedAdjacencyMatrix, Map<Service, Integer> serviceMap,
        Service[] indexMap) {

    public static final class Factory {

        private final LinearAlgebra linearAlgebra;

        public Factory(LinearAlgebra linearAlgebra) {
            this.linearAlgebra = requireNonNull(linearAlgebra);
        }

        public Graph build(Edge... edges) {
            var serviceMap = new HashMap<Service, Integer>();
            var distinctServices = new HashSet<>(stream(edges).flatMap(it -> Stream.of(it.from(), it.to())).toList());
            var indexMap = new Service[distinctServices.size() + 1];
            var index = 1;
            for (var service : distinctServices) {
                serviceMap.put(service, index);
                indexMap[index] = service;
                index++;
            }

            var dimensions = new Dimensions(distinctServices.size(), distinctServices.size());
            var adjacencyMatrix = linearAlgebra.matrix(dimensions);
            var unweightedAdjacencyMatrix = linearAlgebra.matrix(dimensions);
            for (var edge : edges) {
                var i = serviceMap.get(edge.from());
                var j = serviceMap.get(edge.to());
                adjacencyMatrix.set(i, j, edge.weight());
                unweightedAdjacencyMatrix.set(i, j, 1);
            }

            return new Graph(adjacencyMatrix, unweightedAdjacencyMatrix, unmodifiableMap(serviceMap), indexMap);
        }

        public Graph parseCsv(String csv) {
            var edges = stream(csv.split(", *")).map(Edge::parse).toArray(Edge[]::new);
            return build(edges);
        }
    }

    public int index(Service service) {
        return serviceMap.get(service);
    }

    private Service service(int index) {
        return indexMap[index];
    }

    public Set<Service> services() {
        return serviceMap.keySet();
    }

    public Set<Edge> edges(Service service) {
        var i = index(service);
        var neighbors = new HashSet<Edge>();
        for (int j = 1; j <= adjacencyMatrix.dimensions().columns(); j++) {
            var weight = adjacencyMatrix.element(i, j);
            if (weight > 0) {
                var to = service(j);
                neighbors.add(new Edge(service, to, weight));
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Graph)) {
            return false;
        }
        return adjacencyMatrix.equals(((Graph) other).adjacencyMatrix);
    }

    @Override
    public int hashCode() {
        return adjacencyMatrix.hashCode();
    }

}