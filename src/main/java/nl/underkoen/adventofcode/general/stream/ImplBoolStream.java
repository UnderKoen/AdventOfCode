package nl.underkoen.adventofcode.general.stream;

import java.util.stream.Stream;

class ImplBoolStream extends ImplEStream<Boolean> implements BoolStream {
    public ImplBoolStream(Stream<Boolean> stream) {
        super(stream);
    }
}
