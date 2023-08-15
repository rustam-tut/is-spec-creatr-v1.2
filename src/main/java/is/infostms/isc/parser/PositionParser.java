package is.infostms.isc.parser;

import is.infostms.isc.model.Position;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class PositionParser {

    protected final File sourceFile;

    protected final ArrayList<Position> positions = new ArrayList<>();

    protected boolean PARSE_ENTIRE_FILE;

    public PositionParser(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public PositionParser(File sourceFile, boolean entireFile) {
        this.sourceFile = sourceFile;
        this.PARSE_ENTIRE_FILE = entireFile;
    }


    public List<Position> getPositions() {
        return positions;
    }

    public abstract void parse();

}
