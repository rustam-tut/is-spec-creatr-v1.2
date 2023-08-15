package is.infostms.isc;

import is.infostms.isc.model.Position;
import is.infostms.isc.parser.PositionParser;
import is.infostms.isc.parser.PositionParserXLS;

import java.io.File;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Main {
    private static final String fName = "C:\\Users\\Acer\\Desktop\\инфстмс\\for java\\01.08.23 Запрос КП 2324 Валеев.xlsx";

    public static void main(String[] args) {
        File file = new File(fName);

        PositionParser pp1 = new PositionParserXLS(file);
        PositionParser pp2 = new PositionParserXLS(file, true);
        PositionParser pp3 = new PositionParserXLS(file, 0, 1, 3);

        pp1.parse();
        pp2.parse();
        pp3.parse();

        List<Position> positions1 = pp1.getPositions();
        List<Position> positions2 = pp2.getPositions();
        List<Position> positions3 = pp3.getPositions();

        positions1.forEach(System.out::println);
        System.out.println(positions1.size());
        positions2.forEach(System.out::println);
        System.out.println(positions2.size());
        positions3.forEach(System.out::println);
        System.out.println(positions3.size());
    }


}