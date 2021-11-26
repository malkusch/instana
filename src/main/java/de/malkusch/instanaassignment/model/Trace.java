package de.malkusch.instanaassignment.model;

import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableList;

import java.util.LinkedList;
import java.util.List;

public record Trace(List<Service> services) {

    public Trace(List<Service> services) {
        if (services.size() < 2) {
            throw new IllegalArgumentException("Trace must be between at least two services");
        }
        this.services = unmodifiableList(services);
    }

    public static Trace parse(String trace) {
        var services = stream(trace.split("-")).map(Service::new).toList();
        return new Trace(services);
    }
    
    public Trace prepend(Service service) {
        var prepended = new LinkedList<>(services());
        prepended.addFirst(service);
        return new Trace(prepended);
    }
}
