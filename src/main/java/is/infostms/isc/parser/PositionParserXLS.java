package is.infostms.isc.parser;


import is.infostms.isc.model.Position;
import is.infostms.isc.model.PositionBuilder;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

import static is.infostms.isc.parser.PositionParserXLSColumnNames.*;

public class PositionParserXLS extends PositionParser{

    private Workbook workbook;

    private final TreeSet<Integer> sheetNumsToParse = new TreeSet<>();

    public PositionParserXLS(File sourceFile) {
        super(sourceFile);
        sheetNumsToParse.add(0);
    }

    public PositionParserXLS(File sourceFile, boolean entireFile) {
        super(sourceFile, entireFile);
    }

    public PositionParserXLS(File sourceFile, int... sheetNums) {
        super(sourceFile);
        for (int nums: sheetNums) {
            sheetNumsToParse.add(nums);
        }
    }

    @Override
    public void parse() {
        initWorkbook();
        updateSheetNumsToParse();
        for (int sheetNum: sheetNumsToParse) {
            List<Position> positionsFromSheet = parseSheet(sheetNum);
            if (positionsFromSheet == null) {
                continue;
            }
            positions.addAll(positionsFromSheet);
        }
        positions.trimToSize();
    }

    private List<Position> parseSheet(int sheetNum) {
        Sheet sheet = workbook.getSheetAt(sheetNum);
        Map<String, int[]> colNames = createColumnNameToNumMap(sheet);
        if (colNames == null) {
            return null;
        }
        Map<Integer, BiConsumer<Position, Double>> doubleSetters = new HashMap<>();
        Map<Integer, BiConsumer<Position, String>> stringSetters = new HashMap<>();
        for (Map.Entry<String, int[]> pair: colNames.entrySet()) {
            String colName = pair.getKey();
            if (staticDoubleSetters.containsKey(colName)) {
                doubleSetters.put(pair.getValue()[1], staticDoubleSetters.get(colName));
            } else if (staticStringSetters.containsKey(colName)) {
                stringSetters.put(pair.getValue()[1], staticStringSetters.get(colName));
            }
        }
        int realLastRowNum = getRealLastRowNum(sheet, colNames) + 1;
        PositionBuilder positionBuilder = PositionBuilder.of(Position::new);
        List<Position> sheetPositions = new ArrayList<>();
        for (int i = colNames.values().iterator().next()[0] + 1; i < realLastRowNum; i++) {
            Row row = sheet.getRow(i);
            for (Map.Entry<Integer, BiConsumer<Position, Double>> pair: doubleSetters.entrySet()) {
                Cell cell = row.getCell(pair.getKey(), Row.RETURN_BLANK_AS_NULL);
                if (cell == null) continue;
                Double value = null;
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    String preValue = cell.getStringCellValue().trim().replaceAll("[^0-9]", "");
                    if (!preValue.isEmpty()) {
                        value = Double.parseDouble(preValue);
                    }
                } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    value = cell.getNumericCellValue();
                }
                positionBuilder.with(pair.getValue(), value);
            }
            for (Map.Entry<Integer, BiConsumer<Position, String>> pair: stringSetters.entrySet()) {
                Cell cell = row.getCell(pair.getKey(), Row.RETURN_BLANK_AS_NULL);
                if (cell == null) continue;
                String value = null;
                if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    value = cell.getStringCellValue();
                } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    value = "'" + cell.getNumericCellValue();
                }
                positionBuilder.with(pair.getValue(), value);
            }
            Position position = positionBuilder.build();
            sheetPositions.add(position);
        }
        return sheetPositions;
    }

    private void updateSheetNumsToParse() {
        if (PARSE_ENTIRE_FILE) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                sheetNumsToParse.add(i);
            }
        } else {
            sheetNumsToParse.removeIf(e -> e >= workbook.getNumberOfSheets());
        }
    }

    private Map<String, int[]> createColumnNameToNumMap(Sheet sheet) {
        Map<String, int[]> columnNameToNumMap = new HashMap<>();
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
                if (cell == null || !(cell.getCellType() == Cell.CELL_TYPE_STRING)) continue;
                String colName = cell.getStringCellValue().toLowerCase().trim();
                if (colNames.contains(colName)) {
                    columnNameToNumMap.put(colName, new int[]{i, j});
                }
            }
            if (columnNameToNumMap.size() >= 2) {
                return columnNameToNumMap;
            }
        }
        return null;
    }

    private void initWorkbook() {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(sourceFile);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        } finally {
            workbook = wb;
        }
    }

    private int getRealLastRowNum(Sheet sheet, Map<String, int[]> colNames){
        int realLastNum = 0;
        for (int[] coord: colNames.values()) {
            int lastRowNum = -1;
            for (int i = sheet.getLastRowNum(); i > coord[0] || i > realLastNum; i--) {
                if (sheet.getRow(i).getCell(coord[1], Row.RETURN_BLANK_AS_NULL) != null) {
                    lastRowNum = i;
                    break;
                }
            }
            if (lastRowNum > realLastNum) {
                realLastNum = lastRowNum;
            }
        }
        return realLastNum;
    }
}
