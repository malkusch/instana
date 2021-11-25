package de.malkusch.instanaassignment.model;

import static java.util.Arrays.stream;

import java.util.List;

public record Trace(List<Service> service) {

    public Trace(List<Service> service) {
        if (service.size() < 2) {
            throw new IllegalArgumentException("Trace must be between at least two services");
        }
        this.service = service;
    }

    public static Trace parse(String trace) {
        var services = stream(trace.split("-")).map(Service::new).toList();
        return new Trace(services);
    }
}
