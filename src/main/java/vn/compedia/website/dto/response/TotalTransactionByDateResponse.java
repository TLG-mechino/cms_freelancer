package vn.compedia.website.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalTransactionByDateResponse {
    private Integer date;
    private Integer total;
}
