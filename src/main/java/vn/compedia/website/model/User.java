package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "address")
    private String address;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "money_wallet")
    private Double moneyWallet;

    @Column(name = "province_id")
    private Integer provinceId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "commune_id")
    private Integer communeId;

    @Column(name = "experience_amount")
    private Integer experienceAmount;

    @Column(name = "rent_cost")
    private Double rentCost;

    @Column(name = "working_hours")
    private Integer workingHours;

    @Column(name = "time_type_id")
    private Integer timeTypeId;

    @Column(name = "facebook_link")
    private String facebookLink;

    @Column(name = "type_login")
    private String typeLogin;

}
