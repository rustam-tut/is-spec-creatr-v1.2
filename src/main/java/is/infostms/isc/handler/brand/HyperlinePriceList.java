package is.infostms.isc.handler.brand;


import static is.infostms.isc.util.PropertiesLoader.*;

public class HyperlinePriceList extends PriceList {

    public HyperlinePriceList() {
        brandName = "Hyperline";
        fileNamePattern = getString("hprln.fileName.regexp");
        sheetAmount = getInt("hprln.sheet.amount");
        SheetPriceList[] sheetPLs = new SheetPriceList[sheetAmount];

        SheetPriceList sheetPL0 = new SheetPriceList();
        sheetPL0.sheetNum = getInt("hprln.sheet0.num");
        sheetPL0.codeColNum = getInt("hprln.sheet0.code");
        sheetPL0.articleColNum = getInt("hprln.sheet0.article");
        sheetPL0.nameColNum = getInt("hprln.sheet0.name");
        sheetPL0.unitColNum = getInt("hprln.sheet0.unit");
        sheetPL0.startPriceAreaNum = getInt("hprln.sheet0.startPriceArea");
        sheetPL0.endPriceAreaNum = getInt("hprln.sheet0.endPriceArea");
        sheetPL0.initColNums();

        SheetPriceList sheetPL1 = new SheetPriceList();
        sheetPL1.sheetNum = getInt("hprln.sheet1.num");
        sheetPL1.codeColNum = getInt("hprln.sheet1.code");
        sheetPL1.articleColNum = getInt("hprln.sheet1.article");
        sheetPL1.nameColNum = getInt("hprln.sheet1.name");
        sheetPL1.unitColNum = getInt("hprln.sheet1.unit");
        sheetPL1.startPriceAreaNum = getInt("hprln.sheet1.startPriceArea");
        sheetPL1.endPriceAreaNum = getInt("hprln.sheet1.endPriceArea");
        sheetPL1.initColNums();

        SheetPriceList sheetPL2 = new SheetPriceList();
        sheetPL2.sheetNum = getInt("hprln.sheet2.num");
        sheetPL2.codeColNum = getInt("hprln.sheet2.code");
        sheetPL2.articleColNum = getInt("hprln.sheet2.article");
        sheetPL2.nameColNum = getInt("hprln.sheet2.name");
        sheetPL2.unitColNum = getInt("hprln.sheet2.unit");
        sheetPL2.startPriceAreaNum = getInt("hprln.sheet2.startPriceArea");
        sheetPL2.endPriceAreaNum = getInt("hprln.sheet2.endPriceArea");
        sheetPL2.initColNums();

        SheetPriceList sheetPL3 = new SheetPriceList();
        sheetPL3.sheetNum = getInt("hprln.sheet3.num");
        sheetPL3.codeColNum = getInt("hprln.sheet3.code");
        sheetPL3.articleColNum = getInt("hprln.sheet3.article");
        sheetPL3.nameColNum = getInt("hprln.sheet3.name");
        sheetPL3.unitColNum = getInt("hprln.sheet3.unit");
        sheetPL3.startPriceAreaNum = getInt("hprln.sheet3.startPriceArea");
        sheetPL3.endPriceAreaNum = getInt("hprln.sheet3.endPriceArea");
        sheetPL3.initColNums();

        SheetPriceList sheetPL4 = new SheetPriceList();
        sheetPL4.sheetNum = getInt("hprln.sheet4.num");
        sheetPL4.codeColNum = getInt("hprln.sheet4.code");
        sheetPL4.articleColNum = getInt("hprln.sheet4.article");
        sheetPL4.nameColNum = getInt("hprln.sheet4.name");
        sheetPL4.unitColNum = getInt("hprln.sheet4.unit");
        sheetPL4.startPriceAreaNum = getInt("hprln.sheet4.startPriceArea");
        sheetPL4.endPriceAreaNum = getInt("hprln.sheet4.endPriceArea");
        sheetPL4.initColNums();

        sheetPLs[0] = sheetPL0;
        sheetPLs[1] = sheetPL1;
        sheetPLs[2] = sheetPL2;
        sheetPLs[3] = sheetPL3;
        sheetPLs[4] = sheetPL4;
        sheetPriceLists = sheetPLs;
    }
}
