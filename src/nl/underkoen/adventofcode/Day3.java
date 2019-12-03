package nl.underkoen.adventofcode;

import java.util.*;

/**
 * Created by Under_Koen on 02/12/2019.
 */
public class Day3 {
    public static void main(String[] args) {
        Map<List<Integer>, Integer> points = new HashMap<>();
        Map<List<Integer>, Integer> dup = new HashMap<>();

        List<String> input = Utils.getInput(3);
        for (int i = 0; i < input.size(); i++) {
            List<String> path = Arrays.asList(input.get(i).split(","));
            int x = 0;
            int y = 0;
            Map<List<Integer>, Integer> line = new HashMap<>();
            int steps = 0;
            for (int j = 0; j < path.size(); j++) {
                String code = path.get(j);
                int amount = Integer.parseInt(path.get(j).substring(1));
                for (int k = 0; k < amount; k++) {
                    steps++;
                    switch (code.charAt(0)) {
                        case 'R':
                            x++;
                            break;
                        case 'U':
                            y++;
                            break;
                        case 'D':
                            y--;
                            break;
                        case 'L':
                            x--;
                            break;
                        default:
                            System.out.println(code);
                            break;
                    }
                    List<Integer> point = Arrays.asList(x, y);
                    int minSteps = line.containsKey(point) ? Math.min(steps, line.get(point)) : steps;

                    line.put(point, minSteps);
                }
            }

            line.forEach((integers, integer) -> {
                if (points.containsKey(integers)) {
                    dup.put(integers, integer + points.get(integers));
                } else {
                    points.put(integers, integer);
                }
            });
        }

        points.remove(Arrays.asList(0,0));

        int d = dup.entrySet().stream()
                .map(Map.Entry::getValue)
//                .map(entry -> calculateDistance(entry.getKey().get(0), entry.getKey().get(1)))
                .mapToInt(Integer::intValue)
                .min()
                .orElse(0);

        System.out.println(d);
    }

    public static int calculateDistance(int x, int y) {
        return Math.abs(x) + Math.abs(y);
    }
}
