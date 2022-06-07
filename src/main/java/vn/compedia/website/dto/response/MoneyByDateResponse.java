package vn.compedia.website.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MoneyByDateResponse {
    private Integer date;
    private Double money;
}
