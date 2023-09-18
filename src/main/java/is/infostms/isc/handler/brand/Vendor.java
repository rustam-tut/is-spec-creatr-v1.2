package is.infostms.isc.handler.brand;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Vendor {

    DKC("dkc"),

    Hyperline("hyperline"),

    ABN("stub"),

    Anlan("cabeus", "цмо", "d-link", "cablexpert", "rexant");

    private final Set<String> brands;

    Vendor(String... brands) {
        this.brands = Stream.of(brands).collect(Collectors.toSet());
    }

    boolean ofThisPriceList(String brand) {
        return brands.contains(brand);
    }
}

