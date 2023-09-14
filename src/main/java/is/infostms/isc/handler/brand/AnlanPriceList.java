package is.infostms.isc.handler.brand;

import static is.infostms.isc.util.PropertiesLoader.getInt;
import static is.infostms.isc.util.PropertiesLoader.getString;

public class AnlanPriceList extends PriceList {

    // TODO проверить артикул UTP-4P-Cat.5e-SOLID-LSZH-OR-LIGHT
    public AnlanPriceList() {
        fileNamePattern = getString("anln.fileName.regexp");
        sheetAmount = getInt("anln.sheet.amount");

        SheetPriceList[] sheetPLs = new SheetPriceList[sheetAmount];

        SheetPriceList sheetPL0 = new SheetPriceList();
        sheetPL0.maxColNum = getInt("anln.sheet0.maxColNum");
        sheetPL0.sheetNum = getInt("anln.sheet0.num");
        sheetPL0.codeColNum = getInt("anln.sheet0.code");
        sheetPL0.articleColNum = getInt("anln.sheet0.article");
        sheetPL0.nameColNum = getInt("anln.sheet0.name");
        sheetPL0.brandColNum = getInt("anln.sheet0.brand");

        sheetPL0.initColNums();
        sheetPLs[0] = sheetPL0;
        sheetPriceLists = sheetPLs;
    }

}
