package vn.compedia.website.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "register_stickers")
public class RegisterSticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGISTER_STICKERS_ID", nullable = false)
    private Long registerTickersId;

    @Column(name = "JOB_ID")
    private Long jobId;

    @Column(name = "REGISTRATION_TIME")
    private String registrationTime;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "STICKERS_ID")
    private Long stickersId;
}