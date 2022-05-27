package vn.compedia.website.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFICATION_ID")
    private Long id;

    @Column(name = "CONTENT_VN")
    private String contentVn;

    @Column(name = "SENDING_TIME")
    private Date sendingTime;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "OBJECT_ID")
    private Long objectId;

    @Column(name = "TITLE_VN")
    private String titleVn;

    @Column(name = "TITLE_EN")
    private String titleEn;

    @Column(name = "CONTENT_EN")
    private String contentEn;

    @Column(name = "TYPE")
    private Integer type;

    public Notification(String contentVn, Date sendingTime, String username, Integer status, Long objectId, String titleVn, String titleEn, String contentEn, Integer type) {
        this.contentVn = contentVn;
        this.sendingTime = sendingTime;
        this.username = username;
        this.status = status;
        this.objectId = objectId;
        this.titleVn = titleVn;
        this.titleEn = titleEn;
        this.contentEn = contentEn;
        this.type = type;
    }
}
