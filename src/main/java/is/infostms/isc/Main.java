package is.infostms.isc;

import is.infostms.isc.handler.PositionsHandler;
import is.infostms.isc.model.Position;
import is.infostms.isc.parser.PositionParser;
import is.infostms.isc.parser.PositionParserXLS;

import java.io.File;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Main {
    private static final String fName = "C:\\Users\\Acer\\Desktop\\инфстмс\\for java\\01.08.23 Запрос КП 2324 Валеев.xlsx";

    public static void main(String[] args) {
        File file = new File(fName);
        //PositionParser pp1 = new PositionParserXLS(file);
        PositionParser pp2 = new PositionParserXLS(file, true);
        //PositionParser pp3 = new PositionParserXLS(file, 0, 1, 3);
        pp2.parse();
        List<Position> positions2 = pp2.getPositions();
        PositionsHandler ph = PositionsHandler.of(positions2).group();
        Map<Position, Double> map = ph.getAsMap();

        for (Map.Entry<Position, Double> pair: map.entrySet()) {
            System.out.println(pair.getKey() + " -- " + pair.getValue());
        }
    }

}
