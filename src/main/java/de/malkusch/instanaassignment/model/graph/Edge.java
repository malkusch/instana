package de.malkusch.instanaassignment.model.graph;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;

import de.malkusch.instanaassignment.model.Service;

public record Edge(Service from, Service to, int weight) {

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
