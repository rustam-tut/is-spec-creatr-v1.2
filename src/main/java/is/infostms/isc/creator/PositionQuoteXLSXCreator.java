package is.infostms.isc.creator;

import is.infostms.isc.model.Position;
import is.infostms.isc.util.XLSUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.print.DocFlavor;
import java.io.File;
import java.util.*;

public class PositionQuoteXLSXCreator extends PositionsQuoteCreator {

    private static final String ANOTHER = "Аналог";
    private static final String SOURCE = "Исходное";
    private static final String COUNTED = "Расчетное";
    private static final String RETAIL = "Розница";
    private static final String OUR = "Наша цена";
    private static final String TENDER = "ТЕНДЕР";
    private static final String NUM = "№";
    private static final String NAME = "Наименование";
    private static final String BRAND = "Бренд";
    private static final String ARTICLE = "Партномер";
    private static final String AMOUNT = "Кол-во";
    private static final String AM_UNIT = "Ед. изм.";
    private static final String PRICE = "Цена, руб. с НДС";
    public static final String SUM_PRICE = "Сумма, руб. с НДС";
    private static final String SUPPLIER = "Поставщик";
    private static final String SUPP_DATE = "Дата поставки";

    private static final int TABLE_COLUMN_NUMBER = 18;
    
    private static final Map<Integer, Integer> COLUMN_TYPES = new HashMap<>();
    
    private static final String RUBLE_FORMAT = "#,##0.00\\ \"₽\"";
    private static final String PRICE_FORMULA = "L%d*J%d";
    private static final String OUR_PRICE_FORMULA = "N%d*J%d";
    private static final String TENDER_PRICE_FORMULA = "P%d*J%d";
    private static final String PRICE_SUM_FORMULA = "SUM(M%d:M%d)";
    private static final String OUR_PRICE_SUM_FORMULA = "SUM(O%d:O%d)";
    private static final String TENDER_PRICE_SUM_FORMULA = "SUM(Q%d:Q%d)";

    static {
        int[] strings = {1, 2, 3, 4, 5, 6, 8, 10, 17, 18};
        int[] numerics = {0, 7, 9, 11, 13, 15};
        int[] formulas = {12, 14, 16};
        for (int i: strings) {
            COLUMN_TYPES.put(i, Cell.CELL_TYPE_STRING);
        }
        for (int i: numerics) {
            COLUMN_TYPES.put(i, Cell.CELL_TYPE_NUMERIC);
        }
        for (int i: formulas) {
            COLUMN_TYPES.put(i, Cell.CELL_TYPE_FORMULA);
        }
    }
    private final Workbook workbook;

    private Sheet sheet;

    public PositionQuoteXLSXCreator(File file, Collection<Position> positions) {
        super(file, positions);
        workbook = XLSUtil.createWorkBook(file);
    }


    @Override
    public void createQuote() {
        sheet = workbook.createSheet();
        initTable();
        int rowNum = 2;
        int ind = 0;
        Map<Integer, Double> doubleValues = new HashMap<>();
        Map<Integer, String> stringValues = new HashMap<>();

        for (Position position: positions) {
            doubleValues.put(7, position.getSrcAmount());
            doubleValues.put(11, position.getPrice());
            stringValues.put(1, position.getName());
            stringValues.put(2, position.getArticle());
            stringValues.put(3, position.getBrand());
            stringValues.put(8, position.getUnit());
            stringValues.put(18, position.getSupplyingDate());
            for (Map.Entry<Integer, Double> pair: doubleValues.entrySet()) {
                Double val = pair.getValue();
                if (val != null) {
                    sheet.getRow(rowNum).getCell(pair.getKey()).setCellValue(val);
                }
            }
            for (Map.Entry<Integer, String> pair: stringValues.entrySet()) {
                String val = pair.getValue();
                if (val != null) {
                    sheet.getRow(rowNum).getCell(pair.getKey()).setCellValue(val);
                }
            }
            rowNum++;
        }
        XLSUtil.createXLSXFile(workbook, new File(file.getAbsolutePath()
                .replace(file.getName(), file.getName().replaceAll("\\.xls[a-z]*", 21 + ".xls"))));



    }

    // TODO: 18.09.2023 партицировать этот огромный метод
    private void initTable() {
        Row row0 = sheet.createRow(0);
        row0.createCell(4).setCellValue(ANOTHER);
        row0.createCell(7).setCellValue(SOURCE);
        row0.createCell(9).setCellValue(COUNTED);
        row0.createCell(11).setCellValue(RETAIL);
        row0.createCell(13).setCellValue(OUR);
        row0.createCell(15).setCellValue(TENDER);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 16));
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue(NUM);
        row1.createCell(1).setCellValue(NAME);
        row1.createCell(2).setCellValue(BRAND);
        row1.createCell(3).setCellValue(ARTICLE);
        row1.createCell(4).setCellValue(NAME);
        row1.createCell(5).setCellValue(ARTICLE);
        row1.createCell(6).setCellValue(BRAND);
        row1.createCell(7).setCellValue(AMOUNT);
        row1.createCell(8).setCellValue(AM_UNIT);
        row1.createCell(9).setCellValue(AMOUNT);
        row1.createCell(10).setCellValue(AM_UNIT);
        row1.createCell(11).setCellValue(PRICE);
        row1.createCell(12).setCellValue(SUM_PRICE);
        row1.createCell(13).setCellValue(PRICE);
        row1.createCell(14).setCellValue(SUM_PRICE);
        row1.createCell(15).setCellValue(PRICE);
        row1.createCell(16).setCellValue(SUM_PRICE);
        row1.createCell(17).setCellValue(SUPPLIER);
        row1.createCell(18).setCellValue(SUPP_DATE);
        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 22000);
        sheet.setColumnWidth(2, 3000);
        sheet.setColumnWidth(3, 7000);
        sheet.setColumnWidth(4, 11000);
        sheet.setColumnWidth(5, 7000);
        sheet.setColumnWidth(6, 7000);
        sheet.setColumnWidth(7, 2000);
        sheet.setColumnWidth(8, 2000);
        sheet.setColumnWidth(9, 2000);
        sheet.setColumnWidth(10, 2000);
        for (int i = 11; i < 19; i++) {
            sheet.setColumnWidth(i, 4200);
        }
        CellStyle cellStyle0 = generateStyleBorder(BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                true,
                true);
        row0.getCell(4).setCellStyle(cellStyle0);
        for (int i = row0.getFirstCellNum(); i < row0.getLastCellNum(); i++) {
            Cell c;
            if ((c = row0.getCell(i)) == null) continue;
            c.setCellStyle(cellStyle0);
        }
        CellStyle cs1 = generateStyleBorder(BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.MEDIUM.ordinal(),
                true,
                true);
        for (int i = row1.getFirstCellNum(); i < TABLE_COLUMN_NUMBER; i++) {
            Cell c;
            if ((c = row1.getCell(i)) == null) continue;
            c.setCellStyle(cs1);
        }
        CellStyle cs2 = generateStyleBorder(BorderStyle.MEDIUM.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.MEDIUM.ordinal(),
                true,
                true);
        row1.getCell(row1.getLastCellNum() - 1).setCellStyle(cs2);
        int tableSizeLastNum = positions.size() + 2;
        CellStyle csInTbl = generateStyleBorder(BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                false,
                false);
        for (int i = 2; i < tableSizeLastNum; i++) {
            Row r = sheet.createRow(i);
            for (int j = 0; j < TABLE_COLUMN_NUMBER + 1; j++) {
                Cell c = r.createCell(j);
                c.setCellStyle(csInTbl);
            }
        }
        CellStyle csRght = generateStyleBorder(BorderStyle.MEDIUM.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                false,
                false);
        for (int i = 2; i < tableSizeLastNum; i++) {
            Row r = sheet.getRow(i);
            if (r == null) continue;
            r.getCell(18).setCellStyle(csRght);
        }
        CellStyle csBtm = generateStyleBorder(BorderStyle.THIN.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.MEDIUM.ordinal(),
                BorderStyle.THIN.ordinal(),
                false,
                false);
        Row row = sheet.getRow(tableSizeLastNum - 1);
        for (int i = 0; i < TABLE_COLUMN_NUMBER; i++) {
            row.getCell(i).setCellStyle(csBtm);
        }
        CellStyle csLast = generateStyleBorder(BorderStyle.MEDIUM.ordinal(),
                BorderStyle.THIN.ordinal(),
                BorderStyle.MEDIUM.ordinal(),
                BorderStyle.THIN.ordinal(),
                false,
                false);
        sheet.getRow(tableSizeLastNum - 1).getCell(TABLE_COLUMN_NUMBER).setCellStyle(csLast);

        for (int i = 2; i < tableSizeLastNum; i++) {
            Row r = sheet.getRow(i);
            for (int j = 0; j < TABLE_COLUMN_NUMBER + 1; j++) {
                r.getCell(j).setCellType(COLUMN_TYPES.get(j));
            }
        }
        short rubFormatCode = workbook.createDataFormat().getFormat(RUBLE_FORMAT);
        for (int i = 2; i < tableSizeLastNum; i++) {
            Row r = sheet.getRow(i);
            for (int j = 11; j < 17; j++) {
                Cell c = r.getCell(j);
                CellStyle cs = getStyleCopy(c.getCellStyle());
                cs.setDataFormat(rubFormatCode);
                c.setCellStyle(cs);
            }
        }
        for (int i = 2; i < tableSizeLastNum; i++) {
            Row r = sheet.getRow(i);
            int trueI = i + 1;
            r.getCell(12).setCellFormula(String.format(PRICE_FORMULA, trueI, trueI));
            r.getCell(14).setCellFormula(String.format(OUR_PRICE_FORMULA, trueI, trueI));
            r.getCell(16).setCellFormula(String.format(TENDER_PRICE_FORMULA, trueI, trueI));
        }
        CellStyle csSum = generateStyleBorder(BorderStyle.NONE.ordinal(),
                BorderStyle.NONE.ordinal(),
                BorderStyle.NONE.ordinal(),
                BorderStyle.MEDIUM.ordinal(),
                true,
                false);
        csSum.setDataFormat(rubFormatCode);
        Row lRow = sheet.createRow(tableSizeLastNum);
        Cell c1 = lRow.createCell(12);
        Cell c2 = lRow.createCell(14);
        Cell c3 = lRow.createCell(16);
        c1.setCellStyle(csSum);
        c2.setCellStyle(csSum);
        c3.setCellStyle(csSum);
        c1.setCellFormula(String.format(PRICE_SUM_FORMULA, 3, tableSizeLastNum));
        c2.setCellFormula(String.format(OUR_PRICE_SUM_FORMULA, 3, tableSizeLastNum));
        c3.setCellFormula(String.format(TENDER_PRICE_SUM_FORMULA, 3, tableSizeLastNum));
    }

    private CellStyle generateStyleBorder(int bRight, int bLeft, int bBottom, int bTop, boolean isBold,
                                          boolean isImCentre) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Calibri");
        font.setFontHeight((short)220);
        font.setBold(isBold);
        cellStyle.setFont(font);
        if (isImCentre) {
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        }
        cellStyle.setBorderRight((short) bRight);
        cellStyle.setBorderLeft((short) bLeft);
        cellStyle.setBorderBottom((short) bBottom);
        cellStyle.setBorderTop((short) bTop);
        return cellStyle;
    }

    private CellStyle getStyleCopy(CellStyle toCopy) {
        return generateStyleBorder(
                toCopy.getBorderRight(), toCopy.getBorderLeft(), toCopy.getBorderBottom(), toCopy.getBorderTop(),
                false, toCopy.getAlignment() == CellStyle.ALIGN_CENTER);
    }
}
