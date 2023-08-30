package is.infostms.isc.handler.brand;

import java.util.concurrent.TimeUnit;

public enum Brand {

    DKC("dkc"),
    Hyperline("hyperline");

    private final String name;

    Brand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
