package is.infostms.isc.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;


import static org.apache.poi.ss.usermodel.Cell.*;

public final class XLSUtil {

    private XLSUtil() {}

    public static Workbook createWorkBook(File file) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file);
        } catch (InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static Map<String, Integer> createColumnNameToNumMap(Sheet sheet, Set<String> colNames) {
        Map<String, Integer> columnNameToNumMap = new HashMap<>();
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell c = row.getCell(j);
                Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
                if (cell == null || cell.getCellType() != Cell.CELL_TYPE_STRING) continue;
                String colName = cell.getStringCellValue().toLowerCase().replaceAll("[^а-я]", "");
                if (colNames.contains(colName)) {
                    columnNameToNumMap.put(colName, j);
                }
            }
            if (columnNameToNumMap.size() >= 2) {
                return columnNameToNumMap;
            }
        }
        return null;
    }

    public static int getRealFirstRowNum(Sheet sheet, Set<Integer> colNums) {
        int startRowNum = -1;
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            int checkedCount = 0;
            for (int colNum: colNums) {
                Cell cell = row.getCell(colNum, Row.RETURN_BLANK_AS_NULL);
                if (cell != null) {
                    checkedCount++;
                }
            }
            if (checkedCount == colNums.size()) {
                startRowNum = i;
                break;
            }
        }
        return startRowNum + 1;
    }

    public static int getRealLastRowNum(Sheet sheet, Set<Integer> colNums, int startRowNum){
        int realLastNum = 0;
        for (int col: colNums) {
            int lastRowNum = -1;
            for (int i = sheet.getLastRowNum(); i > startRowNum || i > realLastNum; i--) {
                if (sheet.getRow(i).getCell(col, Row.RETURN_BLANK_AS_NULL) != null) {
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

    public static Double getDoubleCellValue(Cell cell) {
        Double value = null;
        int type = cell.getCellType();
        if (type == CELL_TYPE_STRING) {
            String strValue = cell.getStringCellValue()
                    .replaceAll("[^0-9\\.,]", "").replaceAll(",", ".");
            if (!strValue.isEmpty()) {
                value = Double.parseDouble(strValue);
            }
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

    public static int getMaxNumericValuesColumnNum(Sheet sheet, int startRowNum, int lastRowNum) {
        int maxNumericValuesColumnNum = -1;
        for (int i = startRowNum; i < lastRowNum; i += 5) {
            Row row = sheet.getRow(i);
            if (row == null) continue;
            double maxVal = -1.0;
            int maxNum = -1;
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
                Cell cell = row.getCell(j, Row.RETURN_BLANK_AS_NULL);
                if (cell == null) continue;
                if (cell.getCellType() == CELL_TYPE_NUMERIC || cell.getCellType() == CELL_TYPE_FORMULA) {
                    double val = cell.getNumericCellValue();
                    if (Double.compare(maxVal, val) < -1) {
                        maxVal = val;
                        maxNum = j;
                    }
                }
            }
            maxNumericValuesColumnNum = maxNum;
        }
        return maxNumericValuesColumnNum;
    }

    public static int getMaxNumericValuesColumnNum(Sheet sheet) {
        return getMaxNumericValuesColumnNum(sheet, sheet.getFirstRowNum(), sheet.getLastRowNum());
    }
}
