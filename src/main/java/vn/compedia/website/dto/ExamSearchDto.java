package vn.compedia.website.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExamSearchDto extends BaseSearchDto{
    private Integer status;
    private Long examTypeId;
}
