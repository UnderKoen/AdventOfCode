package nl.underkoen.adventofcode.utils;

import org.jgrapht.Graph;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class GraphUtils {
    private static final EdgeWeighted<Object, Object> DEFAULT_WEIGHT = (v, t, e) -> 1.0;

    public static <V, E, G extends Graph<V, E>> G createGraph(Collection<V> vertexes, Function<V, Collection<V>> edges, Supplier<G> supplier, EdgeWeighted<? super V, ? super E> weight) {
        G graph = supplier.get();

        for (V vertex : vertexes) {
            graph.addVertex(vertex);

            for (V target : edges.apply(vertex)) {
                graph.addVertex(target);
                E edge = graph.addEdge(vertex, target);
                graph.setEdgeWeight(edge, weight.weight(vertex, target, edge));
            }
        }

        return graph;
    }

    public static <V, E, G extends Graph<V, E>> G createGraph(Collection<V> vertexes, Function<V, Collection<V>> edges, Supplier<G> supplier) {
        return createGraph(vertexes, edges, supplier, DEFAULT_WEIGHT);
    }

    public static <V, E, G extends Graph<V, E>> G createGraph(Map<V, Collection<V>> map, Supplier<G> supplier, EdgeWeighted<? super V, ? super E> weight) {
        return createGraph(map.keySet(), map::get, supplier, weight);
    }

    public static <V, E, G extends Graph<V, E>> G createGraph(Map<V, Collection<V>> map, Supplier<G> supplier) {
        return createGraph(map.keySet(), map::get, supplier, DEFAULT_WEIGHT);
    }

    @FunctionalInterface
    public interface EdgeWeighted<V, E> {
        double weight(V vertex, V target, E edge);
    }
}
