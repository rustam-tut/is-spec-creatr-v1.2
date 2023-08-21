package is.infostms.isc.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


import static org.apache.poi.ss.usermodel.Cell.*;

public class XLSUtils {

    private XLSUtils() {}

    public static Workbook createWorkBook(File file) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }



    public static Map<String, int[]> createColumnNameToNumMap(Sheet sheet, Set<String> colNames) {
        Map<String, int[]> columnNameToNumMap = new HashMap<>();
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
                if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING) continue;
                String colName = cell.getStringCellValue().toLowerCase().replaceAll("[^а-я]", "");
                System.out.println(colName);
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

    public static Map<String, Row> getKeyFieldToRowMap(Sheet sheet, String keyColName) {
        int keyColNum = -1;
        int startRowNum = -1;
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum() && keyColNum == -1; i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
                if (cell == null) continue;
                String toCompare = getStringCellValue(cell);
                if (toCompare == null) continue;
                if (keyColName.equalsIgnoreCase(toCompare)) {
                    keyColNum = j;
                    startRowNum = i;
                    break;
                }
            }
        }
        if (keyColNum == -1) return null;
        Map<String, Row> keyFieldToRowMap = new HashMap<>();
        for (int i = startRowNum; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            Cell cell = row.getCell(keyColNum, Row.RETURN_BLANK_AS_NULL);
            if (cell == null) continue;
            keyFieldToRowMap.put(getStringCellValue(cell), row);
        }
        return keyFieldToRowMap;
    }


    public static Double getDoubleCellValue(Cell cell) {
        Double value = null;
        int type = cell.getCellType();
        if (type == CELL_TYPE_STRING) {
            value = Double.parseDouble(cell.getStringCellValue()
                    .replaceAll("[^0-9\\.,]", "").replaceAll(",", "."));
        } else if (type == CELL_TYPE_NUMERIC || type == CELL_TYPE_FORMULA) {
            value = cell.getNumericCellValue();
        }
        return value;
    }

    public static String getStringCellValue(Cell cell) {
        String value = null;
        int type = cell.getCellType();
        if (type == CELL_TYPE_STRING) {
            value = cell.getStringCellValue();
        } else if (type == CELL_TYPE_NUMERIC || type == CELL_TYPE_FORMULA) {
             value = (cell.getNumericCellValue() + "").replaceAll(".0$", "");
        }
        return value;
    }

    public static int getRealLastRowNum(Sheet sheet, Map<String, int[]> colNames){
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
