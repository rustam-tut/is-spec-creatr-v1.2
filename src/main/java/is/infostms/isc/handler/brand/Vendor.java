package is.infostms.isc.handler.brand;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Vendor {

    DKC("dkc"),

    //Hyperline("hyperline"),

    ABN("hyperline", "rexant", "abb", "экопласт", "aten", "skynet", "wrline"),

    Anlan("cabeus", "цмо", "d-link", "cablexpert" ,"алюр", "квт"),

    IEK("iek", "itk");

    //Eurolan("eurolan");

    private final Set<String> brands;

    Vendor(String... brands) {
        this.brands = Stream.of(brands).collect(Collectors.toSet());
    }

    boolean ofThisPriceList(String brand) {
        return brands.contains(brand);
    }
}

