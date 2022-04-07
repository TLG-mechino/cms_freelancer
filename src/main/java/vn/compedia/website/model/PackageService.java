package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "package_service")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PackageService extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PACKAGE_SERVICE_ID")
    private Long packageServiceId;

    @Column(name = "SERVICE_TYPE_ID")
    private Integer serviceTypeId;

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
    private String username;

}
