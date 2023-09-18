package is.infostms.isc.creator;

import is.infostms.isc.model.Position;

import java.io.File;
import java.util.Collection;

public abstract class PositionsQuoteCreator {

    protected File file;

    protected Collection<Position> positions;

    public PositionsQuoteCreator(File file, Collection<Position> positions) {
        this.file = file;
        this.positions = positions;
    }


    public abstract void createQuote();
}
