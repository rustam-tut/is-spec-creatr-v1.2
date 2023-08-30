package is.infostms.isc.handler.brand;

import is.infostms.isc.model.PriceListPosition;
import is.infostms.isc.util.PropertiesLoader;
import is.infostms.isc.util.XLSUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BrandPriceList {

    public static final String BASE_DIR = PropertiesLoader.getString("priceList.baseDir")
            + new SimpleDateFormat("dd.MM.yyyy").format(new Date());


    protected String fileNamePattern;

    protected ColumnsOfSheet[] columnOfSheets;

    protected int sheetAmount;

    protected static class ColumnsOfSheet {
        int sheetNum;
        Set<String> colNames;
        Set<Integer> colNums;
        int codeColNum;
        int articleColNum;
        int nameColNum;
        int unitColNum;
        int amountUnitColNum;
        int maxPriceColNum;

        ColumnsOfSheet(Set<String> notNumericColNames, int codeColNum, int articleColNum,
                       int nameColNum, int unitColNum, int amountUnitColNum) {
            this.colNames = notNumericColNames;
            this.codeColNum = codeColNum;
            this.articleColNum = articleColNum;
            this.nameColNum = nameColNum;
            this.unitColNum = unitColNum;
            this.amountUnitColNum = amountUnitColNum;
        }

        void initColNums() {
            colNums = Stream.of(codeColNum, articleColNum, nameColNum, unitColNum, amountUnitColNum)
                    .filter(v -> v >= 0).collect(Collectors.toSet());
        }
    }

    public void createPriceListPositionsSet() {
        HashMap<String, PriceListPosition> plPositions = new HashMap();
        Workbook workbook = XLSUtil.createWorkBook(getFile());
        for (int i = 0; i < sheetAmount; i++) {
            ColumnsOfSheet colOfSheet = columnOfSheets[i];
            Sheet sheet = workbook.getSheetAt(colOfSheet.sheetNum);
            Map<String, Integer> heads = XLSUtil.createColumnNameToNumMap(sheet, colOfSheet.colNames);
            Set<Integer> colNums = heads != null ? new HashSet<>(heads.values()) : colOfSheet.colNums;
            int firstRowNum = XLSUtil.getRealFirstRowNum(sheet, colNums);
            int lastRowNum = XLSUtil.getRealLastRowNum(sheet, colNums, firstRowNum);
            colOfSheet.maxPriceColNum = XLSUtil.getMaxNumericValuesColNum(sheet, firstRowNum, lastRowNum,
                    Collections.max(colNums) + 1);
            colOfSheet.colNums.add(colOfSheet.maxPriceColNum);
            colNums = colOfSheet.colNums;
            Map<Integer, Cell> colNumCell = new HashMap<>();
            colNums.forEach(v -> colNumCell.put(v, null));
            for (int j = firstRowNum; j < lastRowNum; j++) {
                Row row = sheet.getRow(j);
                if (row == null) continue;
                colNums.forEach(v -> colNumCell.put(v, row.getCell(v, Row.RETURN_BLANK_AS_NULL)));
                PriceListPosition plPosition = new PriceListPosition();
                if (colNumCell.containsKey(colOfSheet.codeColNum)) {
                    plPosition.setCode(XLSUtil.getCellValueAsString(colNumCell.get(colOfSheet.codeColNum)));
                }
                if (colNumCell.containsKey(colOfSheet.articleColNum)) {
                    plPosition.setArticle(XLSUtil.getCellValueAsString(colNumCell.get(colOfSheet.articleColNum)));
                }
                if (colNumCell.containsKey(colOfSheet.nameColNum)) {
                    plPosition.setName(XLSUtil.getCellValueAsString(colNumCell.get(colOfSheet.nameColNum)));
                }
                if (colNumCell.containsKey(colOfSheet.unitColNum)) {
                    plPosition.setUnit(XLSUtil.getCellValueAsString(colNumCell.get(colOfSheet.unitColNum)));
                }
                if (colNumCell.containsKey(colOfSheet.amountUnitColNum)) {
                    plPosition.setAmountUnit(XLSUtil.getCellValueAsDouble(colNumCell.get(colOfSheet.amountUnitColNum)));
                }
                if (colNumCell.containsKey(colOfSheet.maxPriceColNum)) {
                    plPosition.setMaxPrice(XLSUtil.getCellValueAsDouble(colNumCell.get(colOfSheet.maxPriceColNum)));
                }
                plPositions.put(plPosition.getArticle(), plPosition);
            }
        }
        plPositions.forEach((key, value) -> System.out.println(key + " -- " + value));
    }

    // TODO: 28.08.2023 создать util класс для поиска файлов, аналогичный метод и для паттерна имени и для полного имени
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
