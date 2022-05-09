package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "district")
@Getter
@Setter
public class District {
    @Id
    @Column(name = "district_id", nullable = false)
    private Long districtId;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "province_id")
    private Long provinceId;

}