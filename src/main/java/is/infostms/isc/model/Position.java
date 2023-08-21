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

    private String id;

    private String fullName;

    private String brand;

    private String article;

    private Double srcAmount;

    private String srcUnit;

    private Double price;

    private boolean isDoubtful;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position p = (Position) o;
        return p.getArticle().equalsIgnoreCase(getArticle()) && p.fullName.equalsIgnoreCase(fullName)
                && (p.srcUnit == null || p.srcUnit.equalsIgnoreCase(srcUnit));
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, article, srcUnit);
    }
}
