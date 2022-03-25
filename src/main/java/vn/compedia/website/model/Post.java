package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "POSTING_TIME")
    private Date postingTime;
    @Column(name = "BLOCK_COMMENT")
    private Integer blockComment;
    @Column(name = "STATUS")
    private Integer status;

}
