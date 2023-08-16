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

    private Double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position p = (Position) o;
        return p.getArticle().equals(getArticle()) & p.fullName.equals(fullName);

    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName, article);
    }
}
