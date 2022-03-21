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
@Table(name = "package_service")
public class PackageService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PACKAGE_SERVICE_ID")
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "CODE")
    private String code;
    @Column(name = "MONEY")
    private Double money;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "CREATE_DATE")
    private Timestamp createDate;
    @Column(name = "UPDATE_DATE")
    private Timestamp updateDate;
    @Column(name = "UPDATE_BY")
    private String updateBy;
    @Column(name = "EXPIRED_TIME")
    private Integer expiredTime;
    @Column(name = "SERVICE_TYPE_ID")
    private Integer serviceTypeId;

}
