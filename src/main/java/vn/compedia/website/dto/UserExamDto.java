package vn.compedia.website.dto;


import lombok.Getter;
import lombok.Setter;
import vn.compedia.website.model.UserExam;

@Setter
@Getter
public class UserExamDto extends UserExam {
    private String examCode;
    private Long numberFile;
    private Double examScore;
    private Double examMaxScore;
}
