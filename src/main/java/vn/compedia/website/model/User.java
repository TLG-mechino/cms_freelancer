package vn.compedia.website.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

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
    private Double rentCost;

    @Column(name = "WORKING_HOURS")
    private Integer workingHours;

    @Column(name = "TIME_TYPE_ID")
    private Integer timeTypeId;

    @Column(name = "FACEBOOK_LINK")
    private String facebookLink;

    @Column(name = "TYPE_LOGIN")
    private String typeLogin;

    @Column(name = "IS_EDITOR")
    private Integer isEditor;

    @Column(name = "IS_USER")
    private Integer isUser;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "DESCRIPTION_USER")
    private String descriptionUser;

    @Column(name = "LANGUAGE_ID")
    private Integer languageId;

}
