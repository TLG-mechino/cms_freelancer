package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hashtag")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Hashtag extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HASHTAG_ID", nullable = false)
    private Long hashtagId;

    @Column(name = "CODE", length = 20)
    private String code;

    @Column(name = "TITLE_VN", length = 20)
    private String titleVn;

    @Column(name = "TITLE_EN", length = 20)
    private String titleEn;

    @Column(name = "DESCRIPTION_VN", length = 200)
    private String descriptionVn;

    @Column(name = "DESCRIPTION_EN", length = 200)
    private String descriptionEn;

    @Column(name = "STATUS")
    private Integer status;
}