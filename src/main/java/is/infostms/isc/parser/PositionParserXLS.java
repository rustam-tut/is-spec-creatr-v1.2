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

    private final List<Position> positions = new ArrayList<>();

    private Workbook workbook;

    public PositionParserXLS(File sourceFile) {
        super(sourceFile);
    }


    @Override
    public void parse() {
        Map<Integer, BiConsumer<Position, Double>> doubleSetters = new HashMap<>();
        Map<Integer, BiConsumer<Position, String>> stringSetters = new HashMap<>();
        initWorkbook();
        Sheet defaultSheet = workbook.getSheetAt(0);
        Map<String, int[]> colNames = createColumnNameToNumMap(defaultSheet);

        for (Map.Entry<String, int[]> pair: colNames.entrySet()) {
            String colName = pair.getKey();
            if (staticDoubleSetters.containsKey(colName)) {
                doubleSetters.put(pair.getValue()[1], staticDoubleSetters.get(colName));
            } else if (staticStringSetters.containsKey(colName)) {
                stringSetters.put(pair.getValue()[1], staticStringSetters.get(colName));
            }
        }

        PositionBuilder positionBuilder = PositionBuilder.of(Position::new);
        for (int i = colNames.values().iterator().next()[0] + 1; i < defaultSheet.getLastRowNum(); i++) {
            Row row = defaultSheet.getRow(i);
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
            positions.add(position);
        }
        System.out.println(positions);
        System.out.println(positions.size());
    }

    private int getRealLastRowNum(Sheet sheet){
        //TODO finish it
        return 0;
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
                if (PositionParserXLSColumnNames.colNames.contains(colName)) {
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
}
