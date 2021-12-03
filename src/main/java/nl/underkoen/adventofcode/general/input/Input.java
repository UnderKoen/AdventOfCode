package nl.underkoen.adventofcode.general.input;

import nl.underkoen.adventofcode.general.stream.EStream;
import nl.underkoen.adventofcode.utils.InputUtils;

import java.util.List;

public interface Input extends List<String> {
    static Input of(List<String> list) {
        return new ImplInput(list);
    }

    default EStream<Long> asNumbersStream() {
        return InputUtils.asNumberList(this);
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
