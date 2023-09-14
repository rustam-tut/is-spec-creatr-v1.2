package is.infostms.isc.handler.brand;

import is.infostms.isc.model.Position;

import java.util.*;
import java.util.stream.Collectors;


public class PriceListFactory {

    private final Set<Position> positions;

    public PriceListFactory(Set<Position> positions) {
        this.positions = new HashSet<>(positions);
    }

    public Map<PriceList, Set<Position>> createPriceListToPositions(Set<String> brands) {
        Map<PriceList, Set<Position>> priceListToPositions = new HashMap<>();
        for (Vendor vendor: Vendor.values()) {
            Set<String> vendorBrands = brands.stream().filter(vendor::ofThisPriceList).collect(Collectors.toSet());
            if (!vendorBrands.isEmpty()) {
                priceListToPositions.put(getPriceList(vendor), getPositionsByVendorBrands(vendorBrands));
            }
        }
        priceListToPositions.forEach((key, value) -> System.out.println(key.getClass().getName() + " --- " + value.size()));
        return priceListToPositions;
    }

    private PriceList getPriceList(Vendor vendor) {
        switch (vendor) {
            case ABN:
                return new ABNPriceList();
            case DKC:
                return new DKCPriceList();
            case Anlan:
                return new AnlanPriceList();
            case Hyperline:
                return new HyperlinePriceList();
            default:
                return null;
        }
    }

    private Set<Position> getPositionsByVendorBrands(Set<String> brands) {
        Set<Position> positionsByVendorBrands =
                positions.stream().filter(p -> brands.contains(p.getBrand().toLowerCase())).collect(Collectors.toSet());
        positions.removeAll(positionsByVendorBrands);
        return positionsByVendorBrands;
    }
}
