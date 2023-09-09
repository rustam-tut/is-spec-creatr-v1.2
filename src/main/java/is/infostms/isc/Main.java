package is.infostms.isc;

import is.infostms.isc.handler.brand.AnlanPriceList;
import is.infostms.isc.handler.brand.HyperlinePriceList;
import is.infostms.isc.handler.brand.PriceList;
import is.infostms.isc.handler.brand.DKCPriceList;

import java.io.IOException;

public class Main {

    private static final String fName = "C:\\Users\\Acer\\Desktop\\инфстмс\\for java\\01.08.23 Запрос КП 2324 Валеев.xlsx";

    public static void main(String[] args) throws IOException {
//        System.out.println(fName);
//        File file = new File(fName);
//        //PositionParser pp1 = new PositionParserXLS(file);
//        PositionParser pp2 = new PositionParserXLS(file, true);
//        //PositionParser pp3 = new PositionParserXLS(file, 0, 1, 3);
//        pp2.parse();
//        List<Position> positions2 = pp2.getPositions();
//        PositionsHandler ph = PositionsHandler.of(positions2).group();
//        Set<Position> positions = ph.getAsGroupedPositions();
//        positions.forEach(System.out::println);
//
        PriceList dkcPriceList = new DKCPriceList();
        PriceList anlanPriceList = new AnlanPriceList();

        dkcPriceList.createPriceListPositionsSet();

    }
}


