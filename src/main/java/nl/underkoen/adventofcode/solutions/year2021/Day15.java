package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.position.multi.Area;
import nl.underkoen.adventofcode.general.stream.EMapStream;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.GraphUtils;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.Map;

public class Day15 extends Solution {
    @Getter private final int day = 15;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{589, 2885};
    }

    @Override
    protected void run(Input input) {
        Map<Position, Character> charMap = input.asCharMap();

        Map<Position, Integer> map = EMapStream.of(charMap)
                .mapValue(Character::getNumericValue)
                .mapMulti((position, value, consumer) -> {
                    for (int x = 0; x < 5; x++) {
                        for (int y = 0; y < 5; y++) {
                            int v = value + x + y;
                            if (v > 9) v = (v + 1) % 10;

                            consumer.accept(position.copyAdd(x * 100, y * 100), v);
                        }
                    }
                })
                .toMap();

        Area<Position> area = new Area<>(charMap.keySet());
        Area<Position> area2 = new Area<>(map.keySet());

        Graph<Position, DefaultWeightedEdge> graph = GraphUtils.createGraph(map.keySet(), area::directNeighbours, () -> new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class), (vertex, target, edge) -> map.get(target));
        Graph<Position, DefaultWeightedEdge> graph2 = GraphUtils.createGraph(map.keySet(), area2::directNeighbours, () -> new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class), (vertex, target, edge) -> map.get(target));

        var dijkstra = new DijkstraShortestPath<>(graph);
        var dijkstra2 = new DijkstraShortestPath<>(graph2);

        a = (int) dijkstra.getPath(area.min(), area.max()).getWeight();
        b = (int) dijkstra2.getPath(area2.min(), area2.max()).getWeight();
    }
}