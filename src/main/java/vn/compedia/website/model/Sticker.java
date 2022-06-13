package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "stickers")
@Setter
@Getter
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STICKERS_ID", nullable = false)
    private Long stickersId;

    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @Column(name = "NAME_VN", length = 50)
    private String nameVn;

    @Column(name = "NAME_EN", length = 50)
    private String nameEn;

    @Column(name = "MONEY")
    private Double money;

    @Column(name = "DESCRIPTION_VN", length = 200)
    private String descriptionVn;

    @Column(name = "DESCRIPTION_EN", length = 200)
    private String descriptionEn;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "CREATE_BY", length = 50)
    private String createBy;

    @Column(name = "UPDATE_BY", length = 50)
    private String updateBy;

    @Column(name = "POSITION")
    private Integer position;

    @Column(name = "COLOR", length = 50)
    private String color;

}