package vn.compedia.website.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private Timestamp sendingTime;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "STATUS")
    private Integer status;
}
