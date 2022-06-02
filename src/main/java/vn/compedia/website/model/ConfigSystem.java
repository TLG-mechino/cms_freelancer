package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "config_system")
@Getter
@Setter
public class ConfigSystem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "config_system_id", nullable = false)
    private Long configSystemId;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "status")
    private Integer status;

    @Column(name = "note", length = 100)
    private String note;
}