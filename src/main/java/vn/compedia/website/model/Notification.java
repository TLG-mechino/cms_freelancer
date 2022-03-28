package vn.compedia.website.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "NOTIFICATION")
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
    private String userName;

    @Column(name = "STATUS")
    private Integer status;
}
