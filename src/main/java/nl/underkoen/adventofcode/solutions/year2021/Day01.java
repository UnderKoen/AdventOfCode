package nl.underkoen.adventofcode.solutions.year2021;

import lombok.Getter;
import nl.underkoen.adventofcode.general.map.AbstractMapCollection;
import nl.underkoen.adventofcode.general.map.HashMapList;
import nl.underkoen.adventofcode.general.map.MapList;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day01 extends Solution {
    @Getter private final int day = 1;
    @Getter private final int year = 2021;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{};
    }

    @Override
    protected void run(List<String> input) {
        //TODO write implementation
        MapList<Integer, String> map = new HashMapList<>();
        map.add(10, "Hey");
        map.add(10, "Hi");
        map.add(5, "Hi");
        System.out.println(map.get(5));
    }
}