package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Exam;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExamDto extends Exam {
    private Long stt;
    private String examTypeName;
    private Long numberFile;
}
