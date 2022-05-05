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

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "SENDING_TIME")
    private Date sendingTime;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "OBJECT_ID")
    private Long objectId;

    @Column(name = "TITLE", length = 100)
    private String title;

    @Column(name = "TYPE")
    private Integer type;

    public Notification(String content, Timestamp sendingTime, String username, Integer status, String title, Long objectId, Integer type) {
        this.content = content;
        this.sendingTime = sendingTime;
        this.username = username;
        this.status = status;
        this.title = title;
        this.objectId = objectId;
        this.type = type;
    }

}
