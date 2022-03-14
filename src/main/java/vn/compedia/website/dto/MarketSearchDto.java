package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MarketSearchDto extends BaseSearchDto {
    private Integer status;

    private String textStatus;
    private String textDateNow;
}
