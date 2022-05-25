package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "customer_talk")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTalk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_talk_id", nullable = false)
    private Long customerTalkId;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "image_path", length = 200)
    private String imagePath;

    @Column(name = "position_vn", length = 50)
    private String positionVn;

    @Column(name = "content_vn")
    private String contentVn;

    @Column(name = "position_en", length = 50)
    private String positionEn;

    @Column(name = "content_en", length = 50)
    private String contentEn;

}