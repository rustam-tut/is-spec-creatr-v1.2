package is.infostms.isc.util;

import is.infostms.isc.model.Position;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class PositionBuilder {

    private final Supplier<Position> inst;

    private final List<Consumer<Position>> fields = new LinkedList<>();

    private PositionBuilder(Supplier<Position> inst) {
        this.inst = inst;
    }

    public static PositionBuilder of(Supplier<Position> inst) {
        return new PositionBuilder(inst);

    }


    public <U> PositionBuilder with(BiConsumer<Position, U> setterCons, U value) {;
        Consumer<Position> consumer = ins -> setterCons.accept(ins, value);
        fields.add(consumer);
        return this;
    }

    public Position build() {
        Position result = inst.get();
        fields.forEach(m -> m.accept(result));
        fields.clear();
        return result;
    }
}
