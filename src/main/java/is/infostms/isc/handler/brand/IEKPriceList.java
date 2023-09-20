package is.infostms.isc.handler.brand;

import static is.infostms.isc.util.PropertiesLoader.getInt;
import static is.infostms.isc.util.PropertiesLoader.getString;

public class IEKPriceList extends PriceList{

    public IEKPriceList() {
        brandName = "Iek";
        fileNamePattern = getString("iek.fileName.regexp");
        sheetAmount = getInt("iek.sheet.amount");

        SheetPriceList[] sheetPLs = new SheetPriceList[sheetAmount];

        SheetPriceList sheetPL0 = new SheetPriceList();
        sheetPL0.sheetNum = getInt("iek.sheet0.num");
        sheetPL0.articleColNum = getInt("iek.sheet0.article");
        sheetPL0.nameColNum = getInt("iek.sheet0.name");
        sheetPL0.unitColNum = getInt("iek.sheet0.unit");
        sheetPL0.startPriceAreaNum = getInt("iek.sheet0.startPriceArea");
        sheetPL0.endPriceAreaNum = getInt("iek.sheet0.endPriceArea");
        sheetPL0.initColNums();
        sheetPLs[0] = sheetPL0;
        sheetPriceLists = sheetPLs;
    }
}
