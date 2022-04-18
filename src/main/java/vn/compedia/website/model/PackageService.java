package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "package_service")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PackageService extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PACKAGE_SERVICE_ID")
    private Long packageServiceId;

    @Column(name = "CODE")
    private String code;

    @Column(name = "MONEY")
    private Double money;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "NAME_VN", length = 100)
    private String nameVn;

    @Column(name = "DESCRIPTION_VN")
    private String descriptionVn;

    @Column(name = "DESCRIPTION_EN")
    private String descriptionEn;

    @Column(name = "LIMIT_POST")
    private Boolean limitPost;

    @Column(name = "LIMIT_COMMENT")
    private Boolean limitComment;

    @Column(name = "LIMIT_SHOW")
    private Boolean limitShow;

    @Column(name = "LIMIT_TRANSACTION")
    private Boolean limitTransaction;

    @Column(name = "POST")
    private Integer post;

    @Column(name = "COMMENT")
    private Integer comment;

    @Column(name = "`SHOW`")
    private Integer show;

    @Column(name = "TRANSACTION")
    private Integer transaction;

    @Column(name = "NAME_EN", length = 100)
    private String nameEn;

    @Column(name = "COLOR")
    private String color;
}
