package vn.compedia.website.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "commune")
@Setter
@Getter
public class Commune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commune_id", nullable = false)
    private Long communeId;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "district_id")
    private Long districtId;

}