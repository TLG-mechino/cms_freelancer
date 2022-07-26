package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "exam")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exam extends BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAM_ID", nullable = false)
    private Long examId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "TITLE_VN")
    private String titleVn;

    @Column(name = "TITLE_EN")
    private String titleEn;

    @Column(name = "DESCRIPTION_VN")
    private String descriptionVn;

    @Column(name = "DESCRIPTION_EN")
    private String descriptionEn;

    @Column(name = "SCORE")
    private Double score;

    @Column(name = "EXAM_TYPE_ID")
    private Long examTypeId;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "HASHTAG_ID")
    private Long hashtagId;

    @Column(name = "MAX_SCORE")
    private Double maxScore;

    @Column(name = "TIME")
    private Integer time;

}
