package vn.compedia.website.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserExamSearchDto extends BaseSearchDto{
    private Integer status;
    private Double score;
}
