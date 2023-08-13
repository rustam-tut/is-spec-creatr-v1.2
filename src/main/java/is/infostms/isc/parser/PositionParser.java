package is.infostms.isc.parser;

import java.io.File;

public abstract class PositionParser {

    protected File sourceFile;

    public PositionParser(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public abstract void parse();
}
