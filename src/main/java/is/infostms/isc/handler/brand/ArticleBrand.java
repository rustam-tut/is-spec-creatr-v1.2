package is.infostms.isc.handler.brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ArticleBrand {

    private String article;

    private String brandName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleBrand that = (ArticleBrand) o;
        if (that.article == null || that.brandName == null) return false;
        return article.equalsIgnoreCase(that.article) && brandName.equalsIgnoreCase(that.brandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(article);
    }
}
