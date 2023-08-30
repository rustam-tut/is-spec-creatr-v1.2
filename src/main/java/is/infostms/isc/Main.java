package is.infostms.isc;

import is.infostms.isc.handler.PositionsHandler;
import is.infostms.isc.handler.brand.BrandPriceList;
import is.infostms.isc.handler.brand.DKCPriceList;
import is.infostms.isc.model.Position;
import is.infostms.isc.parser.PositionParser;
import is.infostms.isc.parser.PositionParserXLS;
import is.infostms.isc.util.PropertiesLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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

        BrandPriceList brandPriceList = new DKCPriceList();
        brandPriceList.createPriceListPositionsSet();
    }

}

