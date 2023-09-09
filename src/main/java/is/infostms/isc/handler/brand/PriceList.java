package is.infostms.isc.handler.brand;

import is.infostms.isc.model.Position;
import is.infostms.isc.model.PriceListPosition;
import is.infostms.isc.util.PositionBuilder;
import is.infostms.isc.util.PropertiesLoader;
import is.infostms.isc.util.XLSUtil;
import javafx.geometry.Pos;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class PriceList {

    public static final String BASE_DIR = PropertiesLoader.getString("priceList.baseDir")
            + new SimpleDateFormat("dd.MM.yyyy").format(new Date()) + "\\";

    protected String fileNamePattern;

    protected SheetPriceList[] sheetPriceLists;

    protected int sheetAmount;

    protected static class SheetPriceList {
        int sheetNum = -1;
        int codeColNum = -1;
        int brandColNum = -1;
        int articleColNum = -1;
        int nameColNum = -1;
        int unitColNum = -1;
        int amountUnitColNum = -1;
        int maxColNum = -1;
        int maxPriceColNum = -1;
        Set<Integer> colNums;

        void initColNums() {
            colNums = Stream.of(codeColNum, articleColNum, brandColNum, nameColNum, unitColNum, amountUnitColNum)
                    .filter(v -> v >= 0).collect(Collectors.toSet());
        }

    }

    public void createPriceListPositionsSet() {
        HashMap<String, PriceListPosition> plPositions = new HashMap<>();
        Workbook workbook = XLSUtil.createWorkBook(getFile());
        for (int i = 0; i < sheetAmount; i++) {
            SheetPriceList sheetPriceList = sheetPriceLists[i];
            Sheet sheet = workbook.getSheetAt(sheetPriceList.sheetNum);
            Set<Integer> colNums = sheetPriceList.colNums;
            int firstRowNum = XLSUtil.getRealFirstRowNum(sheet, colNums);
            int lastRowNum = XLSUtil.getRealLastRowNum(sheet, colNums, firstRowNum);
            System.out.println(sheetPriceList.colNums);
            List<Integer> numsForMaxColNum = new ArrayList<>();
            for (int j = 0; j < sheetPriceList.maxColNum; j++) {
                if (!colNums.contains(j)) {
                    numsForMaxColNum.add(j);
                }
            }
            sheetPriceList.maxPriceColNum = XLSUtil.getMaxNumericValuesColNum(sheet, firstRowNum, lastRowNum,
                    numsForMaxColNum);
            sheetPriceList.colNums.add(sheetPriceList.maxPriceColNum);
            Map<Integer, BiConsumer<Position, Double>> dblSetters = new HashMap<>();
            Map<Integer, BiConsumer<Position, String>> strSetters = new HashMap<>();
            if (sheetPriceList.colNums.contains(sheetPriceList.codeColNum)) {
                strSetters.put(sheetPriceList.codeColNum, Position::setCode);
            }
            if (sheetPriceList.colNums.contains(sheetPriceList.articleColNum)) {
                strSetters.put(sheetPriceList.articleColNum, Position::setArticle);
            }
            if (sheetPriceList.colNums.contains(sheetPriceList.nameColNum)) {
                strSetters.put(sheetPriceList.nameColNum, Position::setName);
            }
            if (sheetPriceList.colNums.contains(sheetPriceList.unitColNum)) {
                strSetters.put(sheetPriceList.unitColNum, Position::setUnit);
            }
            if (sheetPriceList.colNums.contains(sheetPriceList.amountUnitColNum)) {
                dblSetters.put(sheetPriceList.amountUnitColNum, Position::setAmountUnit);
            }
            if (sheetPriceList.colNums.contains(sheetPriceList.brandColNum)) {
                strSetters.put(sheetPriceList.brandColNum, Position::setBrand);
            }
            dblSetters.put(sheetPriceList.maxPriceColNum, Position::setPrice);
            PositionBuilder positionBuilder = PositionBuilder.of(PriceListPosition::new);
            for (int j = firstRowNum; j < lastRowNum; j++) {
                Row row = sheet.getRow(j);
                if (row == null) continue;
                for (Map.Entry<Integer, BiConsumer<Position, Double>> pair: dblSetters.entrySet()) {
                    Cell cell = row.getCell(pair.getKey(), Row.RETURN_BLANK_AS_NULL);
                    positionBuilder.with(pair.getValue(), XLSUtil.getCellValueAsDouble(cell));
                }
                for (Map.Entry<Integer, BiConsumer<Position, String>> pair: strSetters.entrySet()) {
                    Cell cell = row.getCell(pair.getKey(), Row.RETURN_BLANK_AS_NULL);
                    positionBuilder.with(pair.getValue(), XLSUtil.getCellValueAsString(cell));
                }

                PriceListPosition plPosition = (PriceListPosition) positionBuilder.build();
                plPositions.put(plPosition.getArticle(), plPosition);
            }
            System.out.println(sheetPriceList.maxPriceColNum);
        }
        //plPositions.forEach((key, value) -> System.out.println(key + " ----  " + value.getBrand()  + "---" + value.getPrice()));
        System.out.println(plPositions.size());
    }

    // TODO: 28.08.2023 создать util класс
    private File getFile() {
        File file = null;
        try {
            File[] files = new File(BASE_DIR).listFiles();
            if (files == null) throw new FileNotFoundException();
            file = Arrays.stream(files).filter(f -> f.getName().matches(fileNamePattern)).findFirst().orElse(null);
        } catch (FileNotFoundException fnfe) {
            // TODO: 27.08.2023 через логгирование
            System.out.println("PRICE LIST DIR NOT FOUNDED. CREATE DIR WITH ACTUAL DATE");
        }
        return file;
    }
}
