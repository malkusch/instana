package de.malkusch.instanaassignment.infrastructure;

import static java.util.Collections.emptyList;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import de.malkusch.instanaassignment.model.CountTracesWithinLatencyService;
import de.malkusch.instanaassignment.model.Graph;
import de.malkusch.instanaassignment.model.Latency;
import de.malkusch.instanaassignment.model.Service;
import de.malkusch.instanaassignment.model.Trace;

public class DfsCountTracesByHopsService implements CountTracesWithinLatencyService {

    private static record Node(Graph graph, Service service) {

        static record Path(List<Node> nodes) {

            public Path() {
                this(emptyList());
            }

            Path append(Node node) {
                var appended = new LinkedList<>(nodes);
                appended.add(node);
                return new Path(appended);
            }

            Trace toTrace() {
                return new Trace(nodes.stream().map(Node::service).toList());
            }
        }

        public List<Path> search(Service target, Latency max) {
            var results = new LinkedList<Path>();
            var path = new Path();
            search(results, path, target, max.asWeight());
            return results;
        }

        private void search(List<Path> results, Path path, Service target, int max) {
            var appended = path.append(this);
            if (service.equals(target) && appended.nodes.size() > 1) {
                results.add(appended);
            }
            edgesWithWeightLessThan(max).forEach(edge -> {

                edge.node.search(results, appended, target, max - edge.weight);
            });
        }

        private static record Edge(int weight, Node node) {

        }

        private Stream<Edge> edgesWithWeightLessThan(int max) {
            return edges().filter(it -> it.weight() < max);
        }

        private Stream<Edge> edges() {
            return graph.edges(service).stream() //
                    .map(it -> new Edge(it.weight(), new Node(graph, it.to())));
        }
    }

    @Override
    public Set<Trace> search(Graph graph, Latency max, Service start, Service end) {
        var startNode = new Node(graph, start);
        var results = startNode.search(end, max);

        var traces = new HashSet<>(results.stream().map(Node.Path::toTrace).toList());

        return traces;
    }

}
