package is.infostms.isc.handler.brand;

import is.infostms.isc.util.XLSUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BrandPriceList {

    public static final String BASE_DIR = "C:\\Users\\Acer\\Desktop\\инфстмс\\for java\\prices\\"
            + new SimpleDateFormat("dd.MM.yyyy").format(new Date());

    protected String fileNamePattern;

    protected ColumnsOfSheet[] columnOfSheets;

    protected int sheetAmount;

    protected static class ColumnsOfSheet {
        Set<String> colNames;
        Set<Integer> colNums;
        int codeColNum;
        int articleColNum;
        int nameColNum;
        int unitColNum;
        int maxPriceColNum;

        ColumnsOfSheet(Set<String> notNumericColNames, int codeColNum, int articleColNum, int nameColNum, int unitColNum) {
            this.colNames = notNumericColNames;
            this.codeColNum = codeColNum;
            this.articleColNum = articleColNum;
            this.nameColNum = nameColNum;
            this.unitColNum = unitColNum;
        }

        void initColNums() {
            colNums = Stream.of(codeColNum, articleColNum, nameColNum, unitColNum)
                    .filter(v -> v >= 0).collect(Collectors.toSet());
        }
        void setMaxPriceColNum(int maxPriceColNum) {
            this.maxPriceColNum = maxPriceColNum;
        }
    }

    public void createPriceListPositionsMap() {
        Map<String, PriceListPosition> plPositions = new HashMap<>();
        Workbook workbook = XLSUtil.createWorkBook(getFile());
        for (int i = 0; i < sheetAmount; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            ColumnsOfSheet colsOfSheet= columnOfSheets[i];
            Map<String, Integer> heads = XLSUtil.createColumnNameToNumMap(sheet, colsOfSheet.colNames);
            int firstRowNum, lastRowNum;
            if (heads != null) {
                Set<Integer> colNums = new HashSet<>(heads.values());
                firstRowNum = XLSUtil.getRealFirstRowNum(sheet, colNums);
                lastRowNum = XLSUtil.getRealLastRowNum(sheet, colNums, firstRowNum);
                colsOfSheet.maxPriceColNum = XLSUtil.getMaxNumericValuesColumnNum(sheet, firstRowNum, lastRowNum);
            } else {
                firstRowNum = XLSUtil.getRealFirstRowNum(sheet, colsOfSheet.colNums);
                // TODO: 27.08.2023 продолжить 
            }
        }
       //return plPositions;
    }


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
