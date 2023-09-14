package is.infostms.isc;

import is.infostms.isc.handler.PositionsHandler;
import is.infostms.isc.handler.brand.AnlanPriceList;
import is.infostms.isc.handler.brand.HyperlinePriceList;
import is.infostms.isc.handler.brand.PriceList;
import is.infostms.isc.model.Position;
import is.infostms.isc.parser.PositionParser;
import is.infostms.isc.parser.PositionParserXLS;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String fName = "C:\\Users\\Acer\\Desktop\\инфстмс\\for java\\01.08.23 Запрос КП 2324 Валеев.xlsx";

    public static void main(String[] args) throws IOException {
        System.out.println(fName);
        File file = new File(fName);
        PositionParser pp2 = new PositionParserXLS(file, true);
        pp2.parse();
        List<Position> positions2 = pp2.getPositions();
        PositionsHandler ph = PositionsHandler.of(positions2)
                .createPositionToSumAmountMap()
                .mapToSet()
                .putPriceListPrices();

    }
}



