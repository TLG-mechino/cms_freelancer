package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "EXAM_TYPE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXAM_TYPE_ID", nullable = false)
    private Long examTypeId;

    @Column(name = "CODE", length = 20)
    private String code;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "STATUS")
    private Integer status;


}
