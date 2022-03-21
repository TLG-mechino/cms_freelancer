package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "package_service")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PackageService{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PACKAGE_SERVICE_ID", nullable = false)
    private Long serviceId;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "CODE", length = 20)
    private String code;

    @Column(name = "MONEY")
    private Double money;

    @Lob
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "USERNAME", length = 20)
    private String username;

    @Column(name = "SERVICE_TYPE_ID")
    private Integer serviceTypeId;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "UPDATE_BY")
    private String updateBy;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;
}