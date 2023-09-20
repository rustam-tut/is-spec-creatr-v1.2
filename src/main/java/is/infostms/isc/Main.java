package is.infostms.isc;

import is.infostms.isc.creator.PositionQuoteXLSXCreator;
import is.infostms.isc.creator.PositionsQuoteCreator;
import is.infostms.isc.handler.PositionsHandler;
import is.infostms.isc.handler.brand.AnlanPriceList;
import is.infostms.isc.handler.brand.ArticleBrand;
import is.infostms.isc.handler.brand.HyperlinePriceList;
import is.infostms.isc.handler.brand.PriceList;
import is.infostms.isc.model.Position;
import is.infostms.isc.parser.PositionParser;
import is.infostms.isc.parser.PositionParserXLS;
import is.infostms.isc.util.FileUtils;
import is.infostms.isc.util.XLSUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final String fName = "C:\\Users\\Acer\\Desktop\\инфстмс\\for java\\01.08.23 Запрос КП 2324 Валеев.xlsx";

    public static void main(String[] args) {
        File file = new File(fName);
        System.out.println(file.getAbsolutePath());
        PositionParserXLS pp2 = new PositionParserXLS(file, true);
        pp2.parse();
        List<Position> positions2 = pp2.getPositions();
        PositionsHandler ph = PositionsHandler.of(positions2);
        ph.createPositionToSumAmountMap().mapToSet();
        ph.putPriceListPrices();
        Set<Position> poses = ph.getAsSet();
        PositionsQuoteCreator positionsQuoteCreator = new PositionQuoteXLSXCreator(file, poses);
        positionsQuoteCreator.createQuote();
    }
}


