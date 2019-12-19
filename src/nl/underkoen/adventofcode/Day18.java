package nl.underkoen.adventofcode;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import nl.underkoen.adventofcode.general.Position;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.MaskSubgraph;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.*;

/**
 * Created by Under_Koen on 17/12/2019.
 */
public class Day18 extends AdventOfCode {
    public static Map<Option, Integer> cache = new HashMap<>();

    public static int test(Graph<Position, DefaultWeightedEdge> graph, Set<Character> collected, BiMap<Character, Position> keys, Map<Position, Character> doors, List<Position> start) {
        if (collected.size() == keys.size()) return 0;

        Option option = new Option(start, collected);
        if (cache.containsKey(option)) {
            return cache.get(option);
        }

        Set<Integer> i = new HashSet<>();
        for (int j = 0; j < start.size(); j++) {
            Position position = start.get(j);

            Graph<Position, DefaultWeightedEdge> subGraph = new MaskSubgraph<>(graph, p -> doors.containsKey(p) && !collected.contains(doors.get(p)), p -> false);
            var dijkstra = new DijkstraShortestPath<>(subGraph).getPaths(position);
            for (Map.Entry<Character, Position> e : keys.entrySet()) {
                Set<Character> c = new HashSet<>(collected);
                if (!c.add(e.getKey())) continue;
                var path = dijkstra.getPath(e.getValue());
                if (path == null) continue;
                for (Position p : path.getVertexList()) {
                    var c2 = keys.inverse().get(p);
                    if (c2 != null) c.add(c2);
                }
                List<Position> s = new ArrayList<>(start);
                s.set(j, e.getValue());
                i.add((int) path.getWeight() + test(graph, c, keys, doors, s));
            }
        }

        int r = Collections.min(i);
        cache.put(option, r);

        return r;
    }

    public static Graph<Position, DefaultWeightedEdge> createGraph(Graph<Position, DefaultWeightedEdge> graph, Map<Position, MapType> tiles, Set<Position> done, Position prev, Position start, int indent) {
        if (graph == null) graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);

        if (done == null) done = Set.of(start);
        done = new HashSet<>(done);

        Set<Position> positions = new HashSet<>();

        for (int i = 0; i < 4; i++) {
            int dx = (i - 2) % 2;
            int dy = (i - 1) % 2;
            Position p = start.copyAdd(dx, dy);

            if (!tiles.containsKey(p) || !done.add(p)) continue;
            positions.add(p);
        }

        if (positions.size() >= 2 || tiles.get(start) != MapType.PATH || prev == null) {
            graph.addVertex(start);
            if (prev != null) {
                graph.setEdgeWeight(graph.addEdge(start, prev), indent);
            }

            prev = start;
            indent = 0;
        }

        for (Position position : positions) {
            createGraph(graph, tiles, done, prev, position, indent + 1);
        }

        return graph;
    }

    @Override
    int getDay() {
        return 18;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{5102, 2282};
    }

    @Override
    void run(List<String> input) {
        Map<Position, MapType> tiles = new HashMap<>();
        BiMap<Character, Position> keys = HashBiMap.create();
        Map<Position, Character> doors = new HashMap<>();

        List<Position> start = new ArrayList<>();

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                Position pos = new Position(x, y);

                if (c == '#') continue;

                MapType type = MapType.PATH;
                if (c == '@') start.add(pos);
                if (Character.isLowerCase(c)) {
                    type = MapType.KEY;
                    keys.put(c, pos);
                } else if (Character.isUpperCase(c)) {
                    type = MapType.DOOR;
                    doors.put(pos, Character.toLowerCase(c));
                }

                tiles.put(pos, type);
            }
        }

        Position s = start.get(0);

        Graph<Position, DefaultWeightedEdge> graph = createGraph(null, tiles, null, null, s, 0);
        a = test(graph, new HashSet<>(), keys, doors, start);

        start.remove(s);
        for (long x = s.getX() - 1; x <= s.getX() + 1; x++) {
            for (long y = s.getY() - 1; y <= s.getY() + 1; y++) {
                Position pos = new Position(x, y);
                switch ((int) pos.distance(s)) {
                    case 0:
                    case 1:
                        tiles.remove(pos);
                        break;
                    case 2:
                        start.add(pos);
                        break;
                }
            }
        }

        graph = null;
        for (Position position : start) graph = createGraph(graph, tiles, null, null, position, 0);
        b = test(graph, new HashSet<>(), keys, doors, start);
    }

    public enum MapType {
        DOOR, KEY, PATH
    }

    public static class Option {
        List<Position> position;
        Set<Character> keys;

        public Option(List<Position> position, Set<Character> keys) {
            this.position = position;
            this.keys = keys;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Option)) return false;

            Option option = (Option) o;

            if (!Objects.equals(position, option.position)) return false;
            return Objects.equals(keys, option.keys);
        }

        @Override
        public int hashCode() {
            int result = position != null ? position.hashCode() : 0;
            result = 31 * result + (keys != null ? keys.hashCode() : 0);
            return result;
        }
    }
}
