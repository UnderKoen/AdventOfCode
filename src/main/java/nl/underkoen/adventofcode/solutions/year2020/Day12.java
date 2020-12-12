package nl.underkoen.adventofcode.solutions.year2020;

import lombok.Getter;
import nl.underkoen.adventofcode.general.Position;
import nl.underkoen.adventofcode.solutions.Solution;

import java.util.List;

public class Day12 extends Solution {
    @Getter private final int day = 12;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{1838, 89936};
    }

    @Override
    protected void run(List<String> input) {
        Position ship = new Position();
        Position shipB = new Position();

        Position waypoint = new Position(10, 1);
        int rot = 0;

        for (String instruction : input) {
            int value = Integer.parseInt(instruction.substring(1));
            switch (instruction.charAt(0)) {
                case 'N' -> {
                    waypoint.addY(value);
                    ship.addY(value);
                }
                case 'S' -> {
                    waypoint.addY(-value);
                    ship.addY(-value);
                }
                case 'E' -> {
                    waypoint.addX(value);
                    ship.addX(value);
                }
                case 'W' -> {
                    waypoint.addX(-value);
                    ship.addX(-value);
                }
                case 'L' -> {
                    rot = (rot - value + 360) % 360;
                    while ((value += 90) <= 360) waypoint.set(waypoint.getY(), -waypoint.getX());
                }
                case 'R' -> {
                    rot = (rot + value) % 360;
                    while ((value -= 90) >= 0) waypoint.set(waypoint.getY(), -waypoint.getX());
                }
                case 'F' -> {
                    shipB.add(waypoint.getX() * value, waypoint.getY() * value);
                    switch (rot) {
                        case 0 -> ship.addX(value);
                        case 90 -> ship.addY(-value);
                        case 180 -> ship.addX(-value);
                        case 270 -> ship.addY(value);
                    }
                }
            }
        }

        a = ship.distanceOrigin();
        b = shipB.distanceOrigin();
    }
}