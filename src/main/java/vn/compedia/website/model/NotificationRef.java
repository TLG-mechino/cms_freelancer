package vn.compedia.website.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "notification_ref")
public class NotificationRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFICATION_REF_ID")
    private Long id;

    @Column(name = "NOTIFICATION_ID")
    private Long notificationId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "STATUS")
    private Integer status;
}
