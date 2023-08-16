package is.infostms.isc.handler;

import is.infostms.isc.model.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionHandler {

    private final List<Position> positions;

    private Map<Position, Double> positionsAmount;

    private PositionHandler(List<Position> positions) {
        this.positions = positions;
    }

    public static PositionHandler of(List<Position> positions) {
        return new PositionHandler(positions);
    }

    public PositionHandler groupToMap() {
        positionsAmount = new HashMap<>(positions.size(), 0.9f);
        positions.forEach(pos -> {
            Double d;
            if ((d = positionsAmount.get(pos)) == null) {
                positionsAmount.put(pos, pos.getSrcAmount());
            } else {
                // TODo
            }
        });
        return this;
    }
    public List<Position> getAsList() {
        return positions;
    }

    public Map<Position, Double> getAsPositionToAmountMap() {
        return null;
    }

}
