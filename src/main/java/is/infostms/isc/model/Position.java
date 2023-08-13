package is.infostms.isc.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
}
