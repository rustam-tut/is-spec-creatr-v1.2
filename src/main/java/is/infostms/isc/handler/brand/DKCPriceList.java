package is.infostms.isc.handler.brand;

import java.util.HashSet;

public class DKCPriceList extends BrandPriceList {
    public DKCPriceList() {
        fileNamePattern = "Прайс-лист от [0-3][0-9]\\.[0-1][0-9]\\.[0-9]{4}\\.xlsx*";
        sheetAmount = 1;
        columnOfSheets = new ColumnsOfSheet[sheetAmount];
        columnOfSheets[0] = new ColumnsOfSheet(new HashSet<String>() {{
            add("");
            add("Описание");
            add("Ед. Изм.");
            add("Кол-во в упаковке");
            add("Цена с НДС, руб./м(шт)");
        }}, -1, 5, 6, 7);
        columnOfSheets[0].initColNums();
    }
}
