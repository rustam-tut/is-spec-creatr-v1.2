package is.infostms.isc.parser;

import is.infostms.isc.model.Position;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;


public final class PositionParserXLSColumnNames {

    static final String FULL_NAME = "наименование";

    static final String BRAND = "бренд";

    static final String ARTICLE = "партномер";

    static final String AMOUNT = "икол-во";

    static final String PRICE = "рцена, руб. с ндс";

    final static Map<String, BiConsumer<Position, Double>> staticDoubleSetters = new HashMap<>();

    final static Map<String, BiConsumer<Position, String>> staticStringSetters = new HashMap<>();

    final static Set<String> colNames = new HashSet<>();

    static {
        staticDoubleSetters.put(AMOUNT, Position::setSrcAmount);
        staticDoubleSetters.put(PRICE, Position::setPrice);
        staticStringSetters.put(FULL_NAME, Position::setFullName);
        staticStringSetters.put(BRAND, Position::setBrand);
        staticStringSetters.put(ARTICLE, Position::setArticle);
        colNames.addAll(staticDoubleSetters.keySet());
        colNames.addAll(staticStringSetters.keySet());
    }
}
