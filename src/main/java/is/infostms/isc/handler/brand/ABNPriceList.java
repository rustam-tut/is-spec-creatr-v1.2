package is.infostms.isc.handler.brand;


import static is.infostms.isc.util.PropertiesLoader.getInt;
import static is.infostms.isc.util.PropertiesLoader.getString;

public class ABNPriceList extends PriceList {

    public ABNPriceList() {
        fileNamePattern = getString("abn.fileName.regexp");
        sheetAmount = getInt("abn.sheet.amount");

        SheetPriceList[] sheetPLs = new SheetPriceList[sheetAmount];

        SheetPriceList sheetPL0 = new SheetPriceList();
        sheetPL0.sheetNum = getInt("abn.sheet0.num");
        sheetPL0.codeColNum = getInt("abn.sheet0.code");
        sheetPL0.articleColNum = getInt("abn.sheet0.article");
        sheetPL0.nameColNum = getInt("abn.sheet0.name");
        sheetPL0.brandColNum = getInt("abn.sheet0.brand");
        sheetPL0.startPriceAreaNum = getInt("abn.sheet0.startPriceArea");
        sheetPL0.endPriceAreaNum = getInt("abn.sheet0.endPriceArea");
        sheetPL0.unitColNum = getInt("abn.sheet0.unit");
        sheetPL0.initColNums();
        sheetPLs[0] = sheetPL0;
        sheetPriceLists = sheetPLs;
    }

}
