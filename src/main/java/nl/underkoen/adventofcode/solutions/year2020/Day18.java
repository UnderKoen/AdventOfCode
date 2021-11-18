package nl.underkoen.adventofcode.solutions.year2020;

import java_cup.runtime.ComplexSymbolFactory;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.underkoen.adventofcode.solutions.Solution;
import nl.underkoen.adventofcode.solutions.year2020.day18.LexerA;
import nl.underkoen.adventofcode.solutions.year2020.day18.LexerB;
import nl.underkoen.adventofcode.solutions.year2020.day18.ParserA;
import nl.underkoen.adventofcode.solutions.year2020.day18.ParserB;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;

public class Day18 extends Solution {
    @Getter private final int day = 18;
    @Getter private final int year = 2020;

    @Override
    public long[] getCorrectOutput() {
        return new long[]{24650385570008L, 158183007916215L};
    }

    @SneakyThrows
    @Override
    protected void run(List<String> input) {
        String str = InputUtils.asString(input);
        ComplexSymbolFactory symbolFactory = new ComplexSymbolFactory();
        LexerA lexerA = new LexerA(str, symbolFactory);
        LexerB lexerB = new LexerB(str, symbolFactory);
        ParserA parserA = new ParserA(lexerA, symbolFactory);
        ParserB parserB = new ParserB(lexerB, symbolFactory);

        a = (Long) parserA.parse().value;
        b = (Long) parserB.parse().value;
    }
}