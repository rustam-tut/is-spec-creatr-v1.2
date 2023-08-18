package is.infostms.isc.handler;

import is.infostms.isc.model.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionsHandler {

    private final List<Position> positions;

    private Map<Position, Double> positionsAmount;

    public static PositionsHandler of(List<Position> positions) {
        return new PositionsHandler(positions);
    }

    private PositionsHandler(List<Position> positions) {
        this.positions = positions;
    }

    public PositionsHandler clean() {
        return this;
    }
    public PositionsHandler group() {
        positionsAmount = new HashMap<>(positions.size(), 0.9f);
        positions.forEach(pos -> {
            Double am;
            if ((am = positionsAmount.get(pos)) == null) {
                positionsAmount.put(pos, pos.getSrcAmount());
            } else {
                positionsAmount.put(pos, pos.getSrcAmount() + am);
            }
        });
        return this;
    }

    public List<Position> getAsList() {
        return positions;
    }

    public Map<Position, Double> getAsMap() {
        return positionsAmount;
    }

}
