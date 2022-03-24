package vn.compedia.website.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.dto.BaseSearchDto;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchDto extends BaseSearchDto {
    private Integer status;
}
