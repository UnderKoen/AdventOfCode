package nl.underkoen.adventofcode.solutions.year2019;

import lombok.Getter;
import nl.underkoen.adventofcode.general.BiHolder;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.MaskSubgraph;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.*;

/**
 * Created by Under_Koen on 19/12/2019.
 */
public class Day20 extends Solution {
    @Getter private final int day = 20;
    @Getter private final int year = 2019;

    public static Map<BiHolder<Position, Integer>, Integer> cache = new HashMap<>();

    public static int check(Graph<Position, DefaultWeightedEdge> top, int indent, Position start, Position end, Map<Position, Position> portals, Set<Position> done, Set<Set<Position>> depth, int o) {
        var e = new BiHolder<>(start, indent);
        if (cache.containsKey(e)) return cache.get(e);
        if (start == null || o > 200) return -1;

        Set<Integer> i = new HashSet<>();
        var edges = top.edgesOf(start);
        for (DefaultWeightedEdge edge : edges) {
            Set<Position> d = new HashSet<>(done);
            Set<Set<Position>> d2 = new HashSet<>(depth);

            Position p = top.getEdgeTarget(edge);
            if (start.equals(p)) continue;
            if (end.equals(p)) {
                if (indent == 0) {
                    i.add((int) top.getEdgeWeight(edge));
                }
                continue;
            }

            boolean inner = (p.getX() > 4 && p.getX() < 100) && (p.getY() > 4 && p.getY() < 100);
            if (!inner && indent <= 0) continue;

            Position po = portals.get(p);
            if (po == null) continue;

            int r = check(top, indent + (inner ? 1 : -1), po, end, portals, d, d2, o + 1);
            if (r == -1) continue;
            r = r + (int) top.getEdgeWeight(edge);
            i.add(r);
        }

        if (i.isEmpty()) return -1;
        int min = Collections.min(i);
        cache.put(new BiHolder<>(start, indent), min);
        return min;
    }

    public static Graph<Position, DefaultWeightedEdge> createGraph(Graph<Position, DefaultWeightedEdge> graph, Set<Position> tiles, Map<Position, String> keys, Set<Position> done, Position prev, Position start, int indent) {
        if (graph == null) graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);

        if (done == null) done = Set.of(start);
        done = new HashSet<>(done);

        Set<Position> positions = new HashSet<>();

        for (int i = 0; i < 4; i++) {
            int dx = (i - 2) % 2;
            int dy = (i - 1) % 2;
            Position p = start.copyAdd(dx, dy);

            if (!keys.containsKey(p) && (!tiles.contains(p)) || !done.add(p)) continue;
            positions.add(p);
        }

        if (keys.containsKey(start)) {
            graph.addVertex(start);
            if (prev != null) {
                graph.setEdgeWeight(graph.addEdge(prev, start), indent - 1);
            }

            prev = start;
            indent = 0;
        }

        for (Position position : positions) {
            createGraph(graph, tiles, keys, done, prev, position, indent + 1);
        }

        return graph;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{568, 6546};
    }

    @Override
    protected void run(List<String> input) {
        Set<Position> tiles = new HashSet<>();
        Map<Position, Character> chars = new HashMap<>();
        Map<Position, String> keys = new HashMap<>();
        Map<String, Set<Position>> dups = new HashMap<>();

        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);
                Position pos = new Position(x, y);

                if (c == ' ' || c == '#') continue;
                chars.put(pos, c);

                if (Character.isLetter(c)) {
                    Position check = pos.copyAdd(-1, 0);
                    Character c2 = chars.get(check);
                    if (c2 != null && Character.isLetter(c2)) {
                        Position p;
                        if (pos.getX() < 4) p = pos;
                        else if (pos.getX() < 50) p = check;
                        else if (pos.getX() < 100) p = pos;
                        else p = check;
                        pos = p;
                        String s = String.format("%s%s", c, c2);
                        keys.put(pos, s);
                        dups.putIfAbsent(s, new HashSet<>());
                        dups.get(s).add(pos);
                    } else {
                        check = pos.copyAdd(0, -1);
                        c2 = chars.get(check);
                        if (c2 != null && Character.isLetter(c2)) {
                            Position p;
                            if (pos.getY() < 4) p = pos;
                            else if (pos.getY() < 50) p = check;
                            else if (pos.getY() < 100) p = pos;
                            else p = check;
                            pos = p;
                            String s = String.format("%s%s", c, c2);
                            keys.put(pos, s);
                            dups.putIfAbsent(s, new HashSet<>());
                            dups.get(s).add(pos);
                        } else {
                            continue;
                        }
                    }
                }

                tiles.add(pos);
            }
        }

        Graph<Position, DefaultWeightedEdge> graph = null;
        for (Position position : keys.keySet()) {
            graph = createGraph(graph, tiles, keys, null, null, position, 0);

        }

        for (Map.Entry<String, Set<Position>> e : dups.entrySet()) {
            Position pO = null;
            for (Position p : e.getValue()) {
                if (pO != null) graph.setEdgeWeight(graph.addEdge(p, pO), 0);
                pO = p;
            }
        }

        Position aa = dups.get("AA").iterator().next();
        Position zz = dups.get("ZZ").iterator().next();

        var dijkstra = new DijkstraShortestPath<>(graph).getPaths(aa);
        a = (long) dijkstra.getWeight(zz) - 1;

        Graph<Position, DefaultWeightedEdge> temp = graph;
        var top = new MaskSubgraph<>(graph, p -> false, e -> temp.getEdgeWeight(e) == 0);

        Map<Position, Position> portals = new HashMap<>();
        for (Set<Position> e : dups.values()) {
            if (e.size() == 1) continue;
            List<Position> t = new ArrayList<>(e);
            Position p1 = t.get(0);
            Position p2 = t.get(1);
            portals.put(p1, p2);
            portals.put(p2, p1);
        }

        b = check(top, 0, aa, zz, portals, Set.of(aa), Set.of(), 0) - 1;
    }
}
