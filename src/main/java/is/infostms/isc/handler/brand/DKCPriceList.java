package is.infostms.isc.handler.brand;

import java.util.Locale;

import static is.infostms.isc.util.PropertiesLoader.*;

public class DKCPriceList extends BrandPriceList {

public DKCPriceList() {
    // TODO снести эту дичь, с yml опробовать, подумать над сериализацией
        fileNamePattern = getString("dkc.fileName.regexp");
        System.out.println(fileNamePattern);
        sheetAmount = getInt("dkc.sheet.amount");
        SheetPriceList[] sheetPLs = new SheetPriceList[sheetAmount];
        SheetPriceList sheetPL0 = new SheetPriceList();
        sheetPL0.sheetNum = getInt("dkc.sheet0.num");
        sheetPL0.codeColNum = getInt("dkc.sheet0.code");
        sheetPL0.articleColNum = getInt("dkc.sheet0.article");
        sheetPL0.nameColNum = getInt("dkc.sheet0.name");
        sheetPL0.unitColNum = getInt("dkc.sheet0.unit");
        sheetPL0.amountUnitColNum = getInt("dkc.sheet0.amountUnit");
        sheetPL0.initColNums();
        sheetPL0.colNames = getStrSet("dkc.sheet0.col0", "dkc.sheet0.col1",
                "dkc.sheet0.col2", "dkc.sheet0.col3", "dkc.sheet0.col4");
        SheetPriceList sheetPL1 = new SheetPriceList();
        sheetPL1.sheetNum = getInt("dkc.sheet1.num");
        sheetPL1.codeColNum = getInt("dkc.sheet1.code");
        sheetPL1.articleColNum = getInt("dkc.sheet1.article");
        sheetPL1.nameColNum = getInt("dkc.sheet1.name");
        sheetPL1.unitColNum = getInt("dkc.sheet1.unit");
        sheetPL1.amountUnitColNum = getInt("dkc.sheet1.amountUnit");
        sheetPL1.initColNums();
        sheetPL1.colNames = getStrSet("dkc.sheet1.col0", "dkc.sheet1.col1",
                "dkc.sheet1.col2", "dkc.sheet1.col3", "dkc.sheet1.col4");
        sheetPLs[0] = sheetPL0;
        sheetPLs[1] = sheetPL1;
        sheetPriceLists = sheetPLs;
    }
}
