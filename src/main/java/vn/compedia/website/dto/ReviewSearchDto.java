package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.dto.BaseSearchDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSearchDto extends BaseSearchDto {
    private Integer startAmount;
    private Integer status;
}
