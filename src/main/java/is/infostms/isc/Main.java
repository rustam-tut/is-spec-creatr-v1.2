package is.infostms.isc;

import is.infostms.isc.model.Position;
import is.infostms.isc.model.PositionBuilder;
import is.infostms.isc.parser.PositionParser;
import is.infostms.isc.parser.PositionParserXLS;
import javafx.geometry.Pos;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

public class Main {
    static Map<Integer, BiConsumer<Position, Double>> doubleSetters = new HashMap<>();
    static Map<Integer, BiConsumer<Position, String>> stringSetters = new HashMap<>();
    private static final String fName = "C:\\Users\\Acer\\Desktop\\инфстмс\\for java\\01.08.23 Запрос КП 2324 Валеев.xlsx";

    public static void main(String[] args) {
        PositionParser pp = new PositionParserXLS(new File(fName));
        pp.parse();
    }


}