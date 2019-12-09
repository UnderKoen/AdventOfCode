package nl.underkoen.adventofcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Under_Koen on 07/12/2019.
 */
public class Day8 extends AdventOfCode {
    @Override
    int getDay() {
        return 8;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1330};
    }

    @Override
    void run(List<String> input) {
        Map<Integer, List<Character>> layers = new HashMap<>();


        int layer = 0;
        int x = 0;
        int y = 0;
        for (char c : input.get(0).toCharArray()) {
            List<Character> l = layers.getOrDefault(layer, new ArrayList<>());
            l.add(c);
            layers.put(layer, l);
            x++;
            if (x == 25) {
                y++;
                x = 0;
                if (y == 6) {
                    layer++;
                    y = 0;
                }
            }
        }

        int min = -1;
        layer = 0;
        for (Map.Entry<Integer, List<Character>> integerListEntry : layers.entrySet()) {
            int min2 = (int) integerListEntry.getValue().stream()
                    .filter(c -> c == '0')
                    .count();
            if (min == -1 || min2 < min) {
                min = min2;
                layer = integerListEntry.getKey();
            }
        }

        int c1 = 0;
        int c2 = 0;
        for (Character character : layers.get(layer)) {
            if (character == '1') c1++;
            if (character == '2') c2++;
        }

        a = c1 * c2;

        List<Character> f = new ArrayList<>();
        for (int i = 0; i < layers.get(0).size(); i++) {
            char k = 0;
            for (int i1 = 0; i1 < layers.size(); i1++) {
                k = layers.get(i1).get(i);
                if (k != '2') {
                    break;
                }
            }
            f.add(k);
        }

        int z = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 25; j++) {
                char k = f.get(z);
                if (k == '0') System.out.print(" ");
                else System.out.print(k);
                z++;
            }
            System.out.println();
        }
    }
}
