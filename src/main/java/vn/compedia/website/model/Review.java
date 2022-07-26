package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "JOB_ID")
    private Long jobId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "STAR_AMOUNT")
    private Integer starAmount;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "REVIEW_TIME")
    private Date reviewTime;

}
