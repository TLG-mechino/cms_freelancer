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

    @Column(name = "position", length = 50)
    private String position;

    @Column(name = "full_name", length = 50)
    private String fullName;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Integer status;

    @Column(name = "image_path", length = 200)
    private String imagePath;
}