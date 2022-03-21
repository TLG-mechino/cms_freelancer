package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ComplainSearchDto extends BaseSearchDto {
    private String title;
    private String content;
    private String complainant;
    private Integer complainType;
    private Integer status;
}
