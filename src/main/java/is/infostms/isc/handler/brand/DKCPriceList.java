package is.infostms.isc.handler.brand;

import static is.infostms.isc.util.PropertiesLoader.*;

public class DKCPriceList extends PriceList {

        public DKCPriceList() {
                brandName = "DKC";
                fileNamePattern = getString("dkc.fileName.regexp");
                sheetAmount = getInt("dkc.sheet.amount");
                SheetPriceList[] sheetPLs = new SheetPriceList[sheetAmount];

                SheetPriceList sheetPL0 = new SheetPriceList();
                sheetPL0.maxColNum = getInt("dkc.sheet0.maxColNum");
                sheetPL0.sheetNum = getInt("dkc.sheet0.num");
                sheetPL0.articleColNum = getInt("dkc.sheet0.article");
                sheetPL0.nameColNum = getInt("dkc.sheet0.name");
                sheetPL0.unitColNum = getInt("dkc.sheet0.unit");
                sheetPL0.amountUnitColNum = getInt("dkc.sheet0.amountUnit");
                sheetPL0.initColNums();

                SheetPriceList sheetPL1 = new SheetPriceList();
                sheetPL1.maxColNum = getInt("dkc.sheet1.maxColNum");
                sheetPL1.sheetNum = getInt("dkc.sheet1.num");
                sheetPL1.articleColNum = getInt("dkc.sheet1.article");
                sheetPL1.nameColNum = getInt("dkc.sheet1.name");
                sheetPL1.unitColNum = getInt("dkc.sheet1.unit");
                sheetPL1.amountUnitColNum = getInt("dkc.sheet1.amountUnit");
                sheetPL1.initColNums();

                sheetPLs[0] = sheetPL0;
                sheetPLs[1] = sheetPL1;

                sheetPriceLists = sheetPLs;
    }
}
