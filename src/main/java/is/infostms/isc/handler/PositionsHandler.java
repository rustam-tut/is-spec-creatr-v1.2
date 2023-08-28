package is.infostms.isc.handler;

import is.infostms.isc.model.Position;

import java.util.*;

public final class PositionsHandler {

    private final List<Position> positions;

    private Map<Position, Double> positionsAmount;

    public static PositionsHandler of(List<Position> positions) {
        return new PositionsHandler(positions);
    }

    private PositionsHandler(List<Position> positions) {
        this.positions = positions;
    }

    public PositionsHandler clean() {
        // todo
        return this;
    }

    public PositionsHandler group() {
        positionsAmount = new HashMap<>();
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

    public Set<Position> getAsGroupedPositions() {
        Set<Position> groupedPositions = new HashSet<>();
        for (Map.Entry<Position, Double> pair: positionsAmount.entrySet()) {
            Position pos = pair.getKey();
            pos.setSrcAmount(pair.getValue());
            groupedPositions.add(pos);
        }
        return groupedPositions;
    }

    public Map<Position, Double> getAsMap() {
        return positionsAmount;
    }

}
