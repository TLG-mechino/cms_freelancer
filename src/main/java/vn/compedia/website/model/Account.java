package vn.compedia.website.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "account")
@NoArgsConstructor
@Setter
@Getter
public class Account extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ACCOUNT_ID")
    private Long accountId;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_LOGIN")
    private int firstLogin;

    @Column(name = "SALT")
    private String salt;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "TYPE")
    private int type;

    @Column(name = "TOKEN")
    private String token;

    public Account(Long accountId, String email, String username, String password, String salt, Integer status) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.status = status;
    }
}
