package vn.compedia.website.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalJobByDateResponse {
    private Integer date;
    private Integer total;
}
