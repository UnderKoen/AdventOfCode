package nl.underkoen.adventofcode.general.input;

import nl.underkoen.adventofcode.general.position.Position;
import nl.underkoen.adventofcode.general.stream.ELongStream;
import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.general.tuple.BiHolder;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public interface Input extends List<String> {
    static Input of(List<String> list) {
        return new ImplInput(list);
    }

    static Input mutable() {
        return of(new ArrayList<>());
    }

    default ELongStream asNumbers() {
        return InputUtils.asNumbers(this);
    }

    default ELongStream asNumbers(String regex) {
        return InputUtils.asNumbers(this, regex);
    }

    default EStream<ELongStream> asLineNumbers() {
        return InputUtils.asLineNumbers(this);
    }

    default EStream<ELongStream> asLineNumbers(String regex) {
        return InputUtils.asLineNumbers(this, regex);
    }

    default EStream<EStream<String>> asSplitLine() {
        return InputUtils.asSplitLine(this);
    }

    default EStream<EStream<String>> asSplitLine(String regex) {
        return InputUtils.asSplitLine(this, regex);
    }

    default EStream<String> asSplit(String regex) {
        return InputUtils.asSplit(this, regex);
    }

    default EStream<String> asSplit() {
        return InputUtils.asSplit(this);
    }

    default EStream<Position> asPositions() {
        return InputUtils.asPositionList(this);
    }

    default EStream<Position> asPositions(String regex) {
        return InputUtils.asPositionList(this, regex);
    }

    default <T> EStream<T> mapChar(BiFunction<Character, Position, T> map) {
        return InputUtils.mapChar(this, map);
    }

    default Map<Position, Character> asCharMap() {
        return InputUtils.asCharMap(this);
    }

    default char[][] as2dArray() {
        return InputUtils.as2dArray(this);
    }

    default EStream<EStream<String>> asRegexGroupList(String regex) {
        return InputUtils.asRegexGroupList(this, regex);
    }

    default EStream<ELongStream> asAllNumbers() {
        return InputUtils.asAllNumbers(this);
    }

    default EStream<Input> asSubInputs() {
        return InputUtils.asSubInputs(this)
                .map(Input::of);
    }

    default EStream<EStream<Character>> asCharacters() {
        return InputUtils.asCharacters(this);
    }

    default EStream<BiHolder<Integer, String>> asIndexedStream() {
        return InputUtils.asIndexedStream(this);
    }

    default String asString() {
        return InputUtils.asString(this);
    }

    //=====Original=====
    @Override
    EStream<String> stream();

    @Override
    EStream<String> parallelStream();
}
