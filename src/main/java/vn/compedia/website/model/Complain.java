package vn.compedia.website.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "COMPLAIN")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Complain extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPLAIN_ID", nullable = false)
    private Long complainId;

    @Column(name = "USERNAME", length = 20)
    private String username;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "NOTE", length = 100)
    private String note;

    @Column(name = "COMPLAIN_TYPE_ID")
    private Integer complainType;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "OBJECT_ID")
    private Long objectId;

    @Column(name = "TYPE")
    private Integer type;
}
