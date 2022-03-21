package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "register_package")
public class RegisterPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGISTER_PACKAGE_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "EXPIRED_TIME")
    private Date expiredTime;
    @Column(name = "MONEY")
    private Double money;
    @Column(name = "PACKAGE_SERVICE_ID")
    private Long packageServiceId;
}
