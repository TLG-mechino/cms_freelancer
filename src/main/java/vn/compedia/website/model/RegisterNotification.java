package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "register_notification")
@Setter
@Getter
public class RegisterNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`REGISTER_NOTIFICATION _ID`", nullable = false)
    private Long registerNotificationId;

    @Column(name = "USERNAME", length = 20)
    private String username;

    @Column(name = "HASHTAG_ID")
    private Long hashtagId;

}