package is.infostms.isc.handler.brand;

import java.util.Objects;

public class PriceListPosition {
     String article;

     String code;

     String name;

     Double maxPrice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceListPosition that = (PriceListPosition) o;
        return article.equals(that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article);
    }
}
