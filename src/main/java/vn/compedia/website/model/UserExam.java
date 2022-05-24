package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_exam")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_EXAM_ID", nullable = false)
    private Long userExamId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EXAM_ID")
    private Long examId;

    @Column(name = "SUBMIT_TIME")
    private Date submitTime;

    @Column(name = "SCORE")
    private Double score;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "STATUS")
    private Integer status;

}
