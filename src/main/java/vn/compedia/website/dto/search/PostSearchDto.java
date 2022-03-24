package vn.compedia.website.dto.search;

import lombok.Getter;
import lombok.Setter;
import vn.compedia.website.dto.BaseSearchDto;

@Getter
@Setter
public class PostSearchDto extends BaseSearchDto {
    private Integer status;
}
