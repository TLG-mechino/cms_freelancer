package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "system_config")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SystemConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "system_config_id")
    private Long systemConfigId;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    private String type;
}
