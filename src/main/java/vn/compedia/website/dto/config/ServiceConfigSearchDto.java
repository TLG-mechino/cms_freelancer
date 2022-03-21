package vn.compedia.website.dto.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.dto.BaseSearchDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceConfigSearchDto extends BaseSearchDto {
    private Integer status;
    private Integer fillServiceType;
}
