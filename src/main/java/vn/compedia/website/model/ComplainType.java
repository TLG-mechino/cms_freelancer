package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "COMPLAIN_TYPE")
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

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private Integer status;
}
