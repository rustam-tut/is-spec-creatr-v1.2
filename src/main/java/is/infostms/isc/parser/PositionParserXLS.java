package is.infostms.isc.parser;


import is.infostms.isc.model.Position;
import is.infostms.isc.model.PositionBuilder;
import is.infostms.isc.util.XLSUtil;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.BiConsumer;

import static is.infostms.isc.util.PositionStaticData.*;

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
        closeWorkbook();
    }

    private List<Position> parseSheet(int sheetNum) {
        Sheet sheet = workbook.getSheetAt(sheetNum);
        Map<String, Integer> tableHeads = XLSUtil.createColumnNameToNumMap(sheet, colNames);
        if (tableHeads == null) {
            return null;
        }
        Map<Integer, BiConsumer<Position, Double>> doubleSetters = new HashMap<>();
        Map<Integer, BiConsumer<Position, String>> stringSetters = new HashMap<>();
        for (Map.Entry<String, Integer> pair: tableHeads.entrySet()) {
            String colName = pair.getKey();
            if (staticDoubleSetters.containsKey(colName)) {
                doubleSetters.put(pair.getValue(), staticDoubleSetters.get(colName));
            } else if (staticStringSetters.containsKey(colName)) {
                stringSetters.put(pair.getValue(), staticStringSetters.get(colName));
            }
        }
        Set<Integer> colNums = new HashSet<>(tableHeads.values());
        int realFirstRowNum = XLSUtil.getRealFirstRowNum(sheet, colNums);
        int realLastRowNum = XLSUtil.getRealLastRowNum(sheet, colNums, realFirstRowNum);
        PositionBuilder positionBuilder = PositionBuilder.of(Position::new);
        List<Position> sheetPositions = new ArrayList<>();
        for (int i = realFirstRowNum; i < realLastRowNum; i++) {
            Row row = sheet.getRow(i);
            for (Map.Entry<Integer, BiConsumer<Position, Double>> pair: doubleSetters.entrySet()) {
                Cell cell = row.getCell(pair.getKey(), Row.RETURN_BLANK_AS_NULL);
                if (cell == null) continue;
                positionBuilder.with(pair.getValue(), XLSUtil.getCellValueAsDouble(cell));
            }
            for (Map.Entry<Integer, BiConsumer<Position, String>> pair: stringSetters.entrySet()) {
                Cell cell = row.getCell(pair.getKey(), Row.RETURN_BLANK_AS_NULL);
                if (cell == null) continue;
                positionBuilder.with(pair.getValue(), XLSUtil.getCellValueAsString(cell));
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

    private void initWorkbook() {
        workbook = XLSUtil.createWorkBook(sourceFile);
    }

    private void closeWorkbook() {
        try {
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
