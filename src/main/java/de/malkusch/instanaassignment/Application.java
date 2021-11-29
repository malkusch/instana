package de.malkusch.instanaassignment;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.function.Supplier;

import de.malkusch.instanaassignment.infrastructure.DfsCountTracesByHopsService;
import de.malkusch.instanaassignment.infrastructure.DijkstraFindShortestTraceService;
import de.malkusch.instanaassignment.infrastructure.GraphFileFactory;
import de.malkusch.instanaassignment.infrastructure.IterativeCalculateLatencyService;
import de.malkusch.instanaassignment.infrastructure.VectorizedCountTracesByHopsService;
import de.malkusch.instanaassignment.infrastructure.linalg.EjmlLinearAlgebra;
import de.malkusch.instanaassignment.model.CalculateLatencyService;
import de.malkusch.instanaassignment.model.CountTracesByHopsService;
import de.malkusch.instanaassignment.model.CountTracesByHopsService.Hops;
import de.malkusch.instanaassignment.model.CountTracesWithinLatencyService;
import de.malkusch.instanaassignment.model.FindShortestTraceService;
import de.malkusch.instanaassignment.model.Latency;
import de.malkusch.instanaassignment.model.NoSuchTraceException;
import de.malkusch.instanaassignment.model.Service;
import de.malkusch.instanaassignment.model.Trace;
import de.malkusch.instanaassignment.model.graph.Graph;
import de.malkusch.instanaassignment.model.graph.Graph.Factory;

public final class Application {

    private final CalculateLatencyService calculateLatencyService;
    private final FindShortestTraceService findShortestTraceService;
    private final CountTracesByHopsService countTracesByHopsService;
    private final Graph graph;
    private final CountTracesWithinLatencyService countTracesWithinLatencyService;

    public Application(Graph graph, FindShortestTraceService findShortestTraceService,
            CountTracesByHopsService countTracesByHopsService, CalculateLatencyService calculateLatencyService,
            CountTracesWithinLatencyService countTracesWithinLatencyService) {

        this.calculateLatencyService = requireNonNull(calculateLatencyService);
        this.findShortestTraceService = requireNonNull(findShortestTraceService);
        this.countTracesByHopsService = requireNonNull(countTracesByHopsService);
        this.graph = requireNonNull(graph);
        this.countTracesWithinLatencyService = requireNonNull(countTracesWithinLatencyService);
    }

    public static void main(String[] args) throws IOException {
        Application app;
        {
            var linearAlgebra = new EjmlLinearAlgebra();
            var graphFactory = new Factory(linearAlgebra);
            var calculateLatencyService = new IterativeCalculateLatencyService();
            var findShortestTraceService = new DijkstraFindShortestTraceService(graphFactory);
            var countTracesByHopsService = new VectorizedCountTracesByHopsService(linearAlgebra);
            var countTracesWithinLatencyService = new DfsCountTracesByHopsService();
            var graphFileFactory = new GraphFileFactory(graphFactory);

            String filePath = null;
            if (args.length > 0) {
                filePath = args[0];
            }
            if (filePath == null || filePath.isBlank()) {
                exit("Please provide a filename as argument");
            }
            Graph graph = null;
            try {
                graph = graphFileFactory.loadFromFile(filePath);
            } catch (NoSuchFileException e) {
                exit(String.format("%s not found, please try e.g. './sample-graph.csv'", filePath));
            }
            app = new Application(graph, findShortestTraceService, countTracesByHopsService, calculateLatencyService,
                    countTracesWithinLatencyService);
        }

        app.calculateLatency("A-B-C");
        app.calculateLatency("A-D");
        app.calculateLatency("A-D-C");
        app.calculateLatency("A-E-B-C-D");
        app.calculateLatency("A-E-D");
        app.countTracesByMaxHops("C", "C", 3);
        app.countTracesByHops("A", "C", 4);
        app.calculateLatencyOfshortesTrace("A", "C");
        app.calculateLatencyOfshortesTrace("B", "B");
        app.countTracesWithinLatency("C", "C", 30);
    }

    public void calculateLatency(String trace) {
        printResult(() -> {
            var result = calculateLatencyService.calculate(graph, Trace.parse(trace));
            return format(result);
        });
    }

    public void countTracesByHops(String start, String end, int hops) {
        printResult(() -> countTracesByHopsService.countByHops(graph, new Hops(hops), new Service(start),
                new Service(end)));
    }

    public void calculateLatencyOfshortesTrace(String start, String end) {
        printResult(() -> {
            var shortestTrace = findShortestTraceService.find(graph, new Service(start), new Service(end));
            return calculateLatencyService.calculate(graph, shortestTrace);
        });
    }

    public void countTracesByMaxHops(String start, String end, int max) {
        printResult(() -> countTracesByHopsService.countByMaxHops(graph, new Hops(max), new Service(start),
                new Service(end)));
    }

    public void countTracesWithinLatency(String start, String edn, int max) {
        printResult(() -> countTracesWithinLatencyService.count(graph, Latency.fromWeight(max), new Service(start),
                new Service(edn)));
    }

    private static String format(Latency latency) {
        return Integer.toString(latency.toWeight());
    }

    private <T> void printResult(Supplier<T> resultSupplier) {
        try {
            Object result = resultSupplier.get();
            if (result instanceof Latency) {
                result = Integer.toString(((Latency) result).toWeight());
            }
            printResult(result);

        } catch (NoSuchTraceException e) {
            printResult("NO SUCH TRACE");
        }
    }

    private int nextResultNumber = 1;

    private void printResult(Object result) {
        System.out.println(String.format("%d. %s", nextResultNumber, result));
        nextResultNumber++;
    }

    private static void exit(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
