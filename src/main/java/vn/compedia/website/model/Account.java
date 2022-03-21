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
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_login")
    private int firstLogin;

    @Column(name = "salt")
    private String salt;

    @Column(name = "status")
    private Integer status;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "type")
    private int type;

    public Account(Long accountId, String email, String username, String password, String salt, Integer status) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.status = status;
    }
}
