package is.infostms.isc.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class PriceListPosition {

     String article;

     String code;

     String name;

    // TODO: 28.08.2023 ввести энамку для штуки, упаковки, бухт и тд
     String unit;

     Double amountUnit;

     Double maxPrice;

     String supplyingDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceListPosition that = (PriceListPosition) o;
        if (article == null && that.article == null) return true;
        if (article == null ^ that.article == null) return false;
        return article.equals(that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article);
    }

    @Override
    public String toString() {
        return code + "\t" + name + "\t" + article + "\t" + unit + "\t" + amountUnit + "\t" + maxPrice;
    }
}
