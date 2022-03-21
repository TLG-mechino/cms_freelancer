package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERNAME", nullable = false, length = 20)
    private String id;

    @Column(name = "IMAGE_PATH", length = 200)
    private String imagePath;

    @Column(name = "FULL_NAME", nullable = false, length = 50)
    private String fullName;

    @Column(name = "PHONE", nullable = false)
    private Integer phone;

    @Column(name = "ADDRESS", length = 100)
    private String address;

    @Column(name = "TOTAL_SCORE")
    private Integer totalScore;

    @Column(name = "MONEY_WALLET")
    private Integer moneyWallet;

    @Column(name = "PROVINCE_ID")
    private Integer provinceId;

    @Column(name = "DISTRICT_ID")
    private Integer districtId;

    @Column(name = "COMMUNE_ID")
    private Integer communeId;

    @Column(name = "EXPERIENCE_AMOUNT")
    private Integer experienceAmount;

    @Column(name = "RENT_COST")
    private Integer rentCost;

    @Column(name = "WORKING_HOURS")
    private Integer workingHours;

    @Column(name = "TIME_TYPE")
    private Integer timeType;

    @Column(name = "FACEBOOK_LINK", length = 200)
    private String facebookLink;

    @Column(name = "TYPE_LOGIN", length = 20)
    private String typeLogin;
}