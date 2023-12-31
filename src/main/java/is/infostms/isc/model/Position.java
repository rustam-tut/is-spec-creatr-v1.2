package is.infostms.isc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Position {

    private String code;

    protected String article;

    private String name;

    private String brand;

    private Double srcAmount;

    private String sourceUnit;

    private String unit;

    private Double amountUnit;

    private Double price;

    private String supplyingDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position p = (Position) o;
        boolean fn = p.name.trim().equalsIgnoreCase(name.trim());
        if (article == null || p.article == null) return fn;
        return p.article.trim().equalsIgnoreCase(article.trim()) && fn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, article);
    }
}
