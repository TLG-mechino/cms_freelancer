package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "complain_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ComplainType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPLAIN_TYPE_ID")
    private Long complainId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "NAME_VN", length = 100)
    private String nameVn;

    @Column(name = "NAME_EN", length = 100)
    private String nameEn;

}
