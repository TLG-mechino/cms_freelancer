package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hashtag_user")
@Setter
@Getter
public class HashtagUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HASHTAG_USER_ID", nullable = false)
    private Long hashtagUserId;

    @Column(name = "USERNAME", length = 20)
    private String username;

    @Column(name = "HASHTAG_ID")
    private Long hashtagId;

    @Column(name = "TESTED")
    private Integer tested;

    @Column(name = "STATUS")
    private Integer status;

}