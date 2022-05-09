package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "province")
@Getter
@Setter
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "province_id", nullable = false)
    private Long provinceId;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "domain", length = 45)
    private String domain;

    @Column(name = "latitude", length = 11)
    private String latitude;

    @Column(name = "longitude", length = 11)
    private String longitude;
}