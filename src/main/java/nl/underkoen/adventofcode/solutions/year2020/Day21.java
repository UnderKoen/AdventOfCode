package nl.underkoen.adventofcode.solutions.year2020;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.underkoen.adventofcode.general.map.collection.HashMapSet;
import nl.underkoen.adventofcode.general.map.collection.MapSet;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.MapUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 extends Solution {
    @Getter private final int day = 21;
    @Getter private final int year = 2020;

    @Override
    public String[] getCorrectOutputText() {
        return new String[]{"1829", "mxkh,gkcqxs,bvh,sp,rgc,krjn,bpbdlmg,tdbcfb"};
    }

    @Override
    protected void run(List<String> input) {
        Set<String> ingredients = new HashSet<>();
        Set<String> allergies = new HashSet<>();
        Map<String, Long> amount = new HashMap<>();

        List<Dish> dishes = input.stream()
                .map(Dish::parse)
                .peek(d -> {
                    ingredients.addAll(d.ingredients);
                    allergies.addAll(d.getAllergies());

                    MapUtils.increaseAll(amount, d.ingredients, 0L);
                })
                .collect(Collectors.toList());

        MapSet<String, String> possible = new HashMapSet<>();
        for (String aller : allergies) {
            possible.put(aller, new HashSet<>(ingredients));
        }

        for (Dish dish : dishes) {
            for (String allergy : dish.getAllergies()) {
                for (String ingredient : ingredients) {
                    if (!dish.getIngredients().contains(ingredient)) possible.delete(allergy, ingredient);
                }
            }
        }

        MapSet<String, String> invert = possible.invert();

        for (String ingredient : ingredients) {
            if (invert.containsKey(ingredient)) continue;
            a += amount.get(ingredient);
        }

        Map<String, String> pairs = invert.reduceSelf();
        textB = pairs.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .reduce((s, s2) -> s + "," + s2)
                .orElseThrow();
    }

    @Getter
    @AllArgsConstructor
    public static class Dish {
        private final Set<String> ingredients;
        private final Set<String> allergies;

        public static Dish parse(String s) {
            String[] split = s.replace(")", "").split("\\(contains ");
            Set<String> ing = Arrays.stream(split[0].split(" ")).collect(Collectors.toSet());
            Set<String> alg = Arrays.stream(split[1].split(", ")).collect(Collectors.toSet());
            return new Dish(ing, alg);
        }
    }
}