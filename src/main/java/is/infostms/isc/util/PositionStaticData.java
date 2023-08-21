package is.infostms.isc.util;

import is.infostms.isc.model.Position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;


public final class PositionStaticData {

    private PositionStaticData() {}

    static final String FULL_NAME = "наименование";

    static final String BRAND = "бренд";

    static final String ARTICLE = "партномер";

    static final String AMOUNT = "иколво";

    static final String SRC_UNIT = "иедизм";

    static final String PRICE = "рценарубсндс";

    public static final Map<String, BiConsumer<Position, Double>> staticDoubleSetters = new HashMap<>();

    public static final Map<String, BiConsumer<Position, String>> staticStringSetters = new HashMap<>();

    public static final Set<String> colNames = new HashSet<>();

    static {
        staticDoubleSetters.put(AMOUNT, Position::setSrcAmount);
        staticDoubleSetters.put(PRICE, Position::setPrice);
        staticStringSetters.put(FULL_NAME, Position::setFullName);
        staticStringSetters.put(BRAND, Position::setBrand);
        staticStringSetters.put(ARTICLE, Position::setArticle);
        staticStringSetters.put(SRC_UNIT, Position::setSrcUnit);
        colNames.addAll(staticDoubleSetters.keySet());
        colNames.addAll(staticStringSetters.keySet());
    }
}
