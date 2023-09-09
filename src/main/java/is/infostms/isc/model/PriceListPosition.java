package is.infostms.isc.model;

import java.util.Objects;

public class PriceListPosition extends Position {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceListPosition that = (PriceListPosition) o;
        if (article == null && that.article == null) return true;
        if (article == null ^ that.article == null) return false;
        return  article.equals(that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article);
    }

}
