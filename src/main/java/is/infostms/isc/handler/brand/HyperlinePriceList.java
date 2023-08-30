package is.infostms.isc.handler.brand;

import java.util.HashSet;

public class HyperlinePriceList extends BrandPriceList {

    public HyperlinePriceList() {
        fileNamePattern = "Прайс-лист от [0-3][0-9]\\.[0-1][0-9]\\.[0-9]{4}\\.xlsx*";
        sheetAmount = 2;
        columnOfSheets = new ColumnsOfSheet[sheetAmount];
        columnOfSheets[0] = new ColumnsOfSheet(new HashSet<String>() {{
            add("");
            add("Описание");
            add("Ед. Изм.");
            add("Кол-во в упаковке");
            add("Цена с НДС, руб./м(шт)");
        }}, -1, 5, 6, 7, 8);
        columnOfSheets[0].initColNums();
        columnOfSheets[0].sheetNum = 0;
        columnOfSheets[1] = new ColumnsOfSheet(new HashSet<String>() {{
            add("Код");
            add("Описание");
            add("Ед. Изм.");
            add("Кол-во в упаковке");
            add("Цена с НДС, руб./м(шт)");
        }}, -1, 5, 6, 7, 8);
        columnOfSheets[1].initColNums();
        columnOfSheets[1].sheetNum = 3;
    }
}
