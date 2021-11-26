package de.malkusch.instanaassignment.infrastructure;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import de.malkusch.instanaassignment.model.graph.Graph;
import de.malkusch.instanaassignment.model.graph.Graph.Factory;

public final class GraphFileFactory {

    private final Graph.Factory graphFactory;

    public GraphFileFactory(Factory graphFactory) {
        this.graphFactory = requireNonNull(graphFactory);
    }

    public Graph loadFromFile(String path) throws NoSuchFileException, IOException {
        var csv = Files.readString(Paths.get(path), UTF_8);
        return graphFactory.parseCsv(csv);
    }

}
