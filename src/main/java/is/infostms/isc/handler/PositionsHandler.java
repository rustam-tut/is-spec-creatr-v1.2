package is.infostms.isc.handler;

import is.infostms.isc.handler.brand.ArticleBrand;
import is.infostms.isc.handler.brand.PriceList;
import is.infostms.isc.handler.brand.PriceListFactory;
import is.infostms.isc.model.Position;
import is.infostms.isc.model.PriceListPosition;
import javafx.geometry.Pos;

import java.util.*;
import java.util.stream.Collectors;

public final class PositionsHandler {

    private final List<Position> positionsList;

    private Map<Position, Double> positionsAmount;

    private Set<Position> positionsSet;

    public static PositionsHandler of(List<Position> positions) {
        return new PositionsHandler(positions);
    }

    private PositionsHandler(List<Position> positions) {
        this.positionsList = positions;
    }

    public PositionsHandler createPositionToSumAmountMap() {
        positionsAmount = new HashMap<>();
        positionsList.forEach(pos -> {
            Double am;
            if ((am = positionsAmount.get(pos)) == null) {
                positionsAmount.put(pos, pos.getSrcAmount());
            } else {
                positionsAmount.put(pos, pos.getSrcAmount() + am);
            }
        });
        return this;
    }

    public PositionsHandler putPriceListPrices() {
        PriceListFactory priceListFactory = new PriceListFactory(positionsSet);
        Set<String> brands = positionsSet.stream().map(p -> p.getBrand().toLowerCase())
                .collect(Collectors.toSet());
        System.out.println(brands);
        Map<PriceList, Set<Position>> priceListToPositions = priceListFactory.createPriceListToPositions(brands);
        Map<ArticleBrand, PriceListPosition> priceListPositions;
        for (Map.Entry<PriceList, Set<Position>> pair: priceListToPositions.entrySet()) {
            // TODO: 14.09.2023 Остановка здесь
        }

        return this;
    }

    public PositionsHandler mapToSet() {
        positionsSet = new HashSet<>();
        for (Map.Entry<Position, Double> pair: positionsAmount.entrySet()) {
            Position pos = pair.getKey();
            pos.setSrcAmount(pair.getValue());
            positionsSet.add(pos);
        }
        return this;
    }


    public Map<Position, Double> getAsMap() {
        return positionsAmount;
    }

    public Set<Position> getAsSet() {
        return positionsSet;
    }
}
