package nl.underkoen.adventofcode.solutions.year2020;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nl.underkoen.adventofcode.general.input.Input;
import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.utils.InputUtils;
import nl.underkoen.adventofcode.utils.MapUtils;
import nl.underkoen.adventofcode.utils.PositionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class Day20 extends Solution {
    @Getter private final int day = 20;
    @Getter private final int year = 2020;

    public static boolean isCorrect(Map<Position, Image> placements) {
        if (placements.size() <= 1) return true;
        for (Map.Entry<Position, Image> entry : placements.entrySet()) {
            Image org = entry.getValue();
            for (Position n : entry.getKey().getNeighbours()) {
                Position diff = entry.getKey().copySub(n);
                if (diff.getX() != 0 && diff.getY() != 0) continue;
                if (!placements.containsKey(n)) continue;

                Image image = placements.get(n);
                Border b1 = org.getBorder(diff);
                Border b2 = image.getBorder(diff.copyMul(-1, -1));
                if (!b1.equals(b2)) return false;
            }
        }
        return true;
    }

    @Override
    public long[] getCorrectOutput() {
        return new long[]{21599955909991L, 2495};
    }

    @Override
    protected void run(Input input) {
        List<List<String>> lists = InputUtils.asSubInputs(input).toList();

        Map<Long, Image> images = new HashMap<>();
        for (List<String> list : lists) {
            if (list.size() == 0) continue;
            String s = list.remove(0);
            long id = Long.parseLong(s.replaceAll("[^\\d]", ""));
            Set<Position> collect = InputUtils.mapChar(list, (c, p) -> c == '#' ? p : null)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            images.put(id, new Image(id, collect));
        }

        Map<Border, List<Image>> borders = new HashMap<>();
        Map<Border, Long> count = new HashMap<>();
        for (Image option : images.values()) {
            MapUtils.increaseAll(count, option.borders, 0L);
            for (Border border : option.borders) {
                MapUtils.add(borders, border, option);
            }
        }

        Set<Image> corners = new HashSet<>();
        for (Image option : images.values()) {
            if (option.borders.stream()
                    .map(count::get)
                    .filter(i -> i == 1)
                    .count() == 4) corners.add(option);
        }

        a = corners.stream().mapToLong(Image::getId).reduce(1, (left, right) -> left * right);

        Map<Image, Set<Image>> neighbours = new HashMap<>();

        for (Image image : images.values()) {
            for (Border border : image.getBorders()) {
                for (Image n : borders.get(border)) {
                    if (n == image) continue;
                    MapUtils.addSet(neighbours, image, n);
                    break;
                }
            }
        }

        Set<Image> outer = images.values().stream()
                .filter(i -> i.borders.stream().map(count::get).anyMatch(l -> l == 1))
                .collect(Collectors.toSet());

        BiMap<Position, Image> map = HashBiMap.create();
        Image start = corners.iterator().next();
        map.put(new Position(), start);
        int dir = 0;
        for (int i = 0; i < outer.size(); i++)
            for (Image image : neighbours.get(start)) {
                if (map.containsValue(image) || !outer.contains(image)) continue;

                Position pos = map.inverse().get(start).copy();
                switch (dir) {
                    case 0 -> pos.addX(1);
                    case 1 -> pos.addY(1);
                    case 2 -> pos.addX(-1);
                    case 3 -> pos.addY(-1);
                }

                map.put(pos, image);

                if (corners.contains(image)) {
                    dir++;
                }

                start = image;
                break;
            }

        while (map.size() != images.size()) {
            for (Image value : images.values()) {
                if (map.containsValue(value)) continue;
                List<Image> collect = neighbours.get(value).stream().filter(map::containsValue).collect(Collectors.toList());
                if (collect.size() < 2) continue;

                Position p1 = map.inverse().get(collect.get(0));
                Position p2 = new Position();
                for (int i = 1; i < collect.size(); i++) {
                    p2 = map.inverse().get(collect.get(i));
                    Position test = p2.copySub(p1);
                    if (Math.abs(test.getX()) == 1 || Math.abs(test.getY()) == 1) break;
                }

                Position p = new Position(p1.getX(), p2.getY());
                if (map.containsKey(p)) p = new Position(p2.getX(), p1.getY());

                map.put(p, value);
            }
        }

        System.out.println("positions done");

        Position p1 = new Position();
        Position p2 = new Position(1, 0);
        Position p3 = new Position(0, 1);

        Map<Position, Image> rotated = new HashMap<>();

        outer:
        for (Image i1 : map.get(p1).getAlts()) {
            rotated.put(p1, i1);
            for (Image i2 : map.get(p2).getAlts()) {
                rotated.put(p2, i2);
                for (Image i3 : map.get(p3).getAlts()) {
                    rotated.put(p3, i3);
                    if (isCorrect(rotated)) break outer;
                }
            }
        }

        System.out.println("start done");

        while (rotated.size() != images.size()) {
            for (Position position : map.keySet()) {
                if (rotated.containsKey(position)) continue;
                for (Image alt : map.get(position).getAlts()) {
                    rotated.put(position, alt);

                    if (isCorrect(rotated)) break;
                    rotated.remove(position);
                }

                if (!rotated.containsKey(position)) break;
            }
        }
        System.out.println("rotation done");

        rotated.values().forEach(Image::removeBorder);

        Set<Position> all = new HashSet<>();

        for (Map.Entry<Position, Image> entry : rotated.entrySet()) {
            all.addAll(entry.getValue().all.stream().map(p -> p.copyAdd(entry.getKey().copyMul(8, 8))).collect(Collectors.toSet()));
        }

        String log = """
                                  #
                #    ##    ##    ###
                 #  #  #  #  #  #""";

        Set<Position> logness = InputUtils.mapChar(Arrays.asList(log.split("\n")), (c, p) -> c == '#' ? p : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<Position> insideLog = new HashSet<>();

        Map2 m = new Map2(all);
        for (Map2 alt : m.getAlts()) {
            all = alt.all;

            Position min = PositionUtils.min(all);
            Position max = PositionUtils.max(all);

            logness:
            for (Position p : PositionUtils.between(min, max)) {
                for (Position position : logness) {
                    position = position.copyAdd(p);
                    if (!all.contains(position)) continue logness;
                }
                for (Position position : logness) {
                    position = position.copyAdd(p);
                    insideLog.add(position);
                }
            }

            System.out.println(insideLog.size());

            b = all.size() - insideLog.size();
            if (insideLog.size() != 0) break;
        }

        Set<Position> positions = all;
        PositionUtils.print(all, (p, b) -> insideLog.contains(p) ? "O" : b ? "#" : ".");
    }

    @EqualsAndHashCode
    @ToString
    public static class Border {
        String dir;

        public Border(String dir) {
            this.dir = dir;
        }

        public static Border horizontal(int y, Set<Position> all) {
            Position p = new Position(0, y);
            StringBuilder str = new StringBuilder();
            for (; p.getX() < 10; p.addX(1)) {
                str.append(all.contains(p) ? "#" : ".");
            }
            return new Border(str.toString());
        }

        public static Border vertical(int x, Set<Position> all) {
            Position p = new Position(x, 0);
            StringBuilder str = new StringBuilder();
            for (; p.getY() < 10; p.addY(1)) {
                str.append(all.contains(p) ? "#" : ".");
            }
            return new Border(str.toString());
        }

        public Border reverse() {
            return new Border(StringUtils.reverse(dir));
        }
    }

    @Getter
    public static class Image {
        long id;
        Set<Position> all;
        List<Border> borders = new ArrayList<>();
        Image flipH;
        Image flipV;
        Image rotateR;

        public Image(long id, Set<Position> all) {
            this.id = id;
            this.all = all;
            borders.add(Border.vertical(9, all));
            borders.add(Border.horizontal(9, all));
            borders.add(Border.vertical(0, all));
            borders.add(Border.horizontal(0, all));
            borders.add(Border.horizontal(0, all).reverse());
            borders.add(Border.horizontal(9, all).reverse());
            borders.add(Border.vertical(0, all).reverse());
            borders.add(Border.vertical(9, all).reverse());
        }

        public Image flipH() {
            if (flipH != null) return flipH;
            Set<Position> flipped = all.stream().map(p -> new Position(9 - p.getX(), p.getY()))
                    .collect(Collectors.toSet());

            return flipH = new Image(id, flipped);
        }

        public Image flipV() {
            if (flipV != null) return flipV;
            Set<Position> flipped = all.stream().map(p -> new Position(p.getX(), 9 - p.getY()))
                    .collect(Collectors.toSet());

            return flipV = new Image(id, flipped);
        }

        public Image rotateR() {
            if (rotateR != null) return rotateR;
            Set<Position> flipped = all.stream().map(p -> new Position(9 - p.getY(), p.getX()))
                    .collect(Collectors.toSet());

            return rotateR = new Image(id, flipped);
        }

        public List<Image> getRots() {
            return List.of(this, rotateR(), rotateR().rotateR(), rotateR().rotateR().rotateR());
        }

        public void removeBorder() {
            all.removeIf(p -> p.getX() == 0);
            all.removeIf(p -> p.getX() == 9);
            all.removeIf(p -> p.getY() == 0);
            all.removeIf(p -> p.getY() == 9);
        }

        public List<Image> getAlts() {
            List<Image> alts = new ArrayList<>();
            alts.addAll(getRots());
            alts.addAll(flipV().getRots());
            alts.addAll(flipH().getRots());
            return alts;
        }

        public Border getBorder(Position diff) {
            int toCheck;

            if (diff.getX() == 1) toCheck = 2;
            else if (diff.getX() == -1) toCheck = 0;
            else if (diff.getY() == 1) toCheck = 3;
            else if (diff.getY() == -1) toCheck = 1;
            else throw new IllegalArgumentException();

            return borders.get(toCheck);
        }

        @Override
        public String toString() {
            return id + "";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof Image)) return false;

            Image image = (Image) o;

            return new EqualsBuilder().append(getId(), image.getId()).isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
        }
    }

    @Getter
    public static class Map2 {
        Set<Position> all;
        Map2 flipH;
        Map2 flipV;
        Map2 rotateR;

        public Map2(Set<Position> all) {
            this.all = all;
        }

        public Map2 flipH() {
            if (flipH != null) return flipH;
            Set<Position> flipped = all.stream().map(p -> new Position(-p.getX(), p.getY()))
                    .collect(Collectors.toSet());

            return flipH = new Map2(flipped);
        }

        public Map2 flipV() {
            if (flipV != null) return flipV;
            Set<Position> flipped = all.stream().map(p -> new Position(p.getX(), -p.getY()))
                    .collect(Collectors.toSet());

            return flipV = new Map2(flipped);
        }

        public Map2 rotateR() {
            if (rotateR != null) return rotateR;
            Set<Position> flipped = all.stream().map(p -> new Position(-p.getY(), p.getX()))
                    .collect(Collectors.toSet());

            return rotateR = new Map2(flipped);
        }

        public List<Map2> getRots() {
            return List.of(this, rotateR(), rotateR().rotateR(), rotateR().rotateR());
        }

        public List<Map2> getAlts() {
            List<Map2> alts = new ArrayList<>();
            alts.addAll(getRots());
            alts.addAll(flipV().getRots());
//            alts.addAll(flipH().getRots());
            return alts;
        }
    }
}