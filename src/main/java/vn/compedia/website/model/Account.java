package vn.compedia.website.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Account extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "user_name")
    private String username;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "salt")
    private String salt;

    @Column(name = "status")
    private Integer status;

    public Account(Long accountId, Long roleId, String fullName, String email, String username, String password, String salt, Integer status) {
        this.accountId = accountId;
        this.roleId = roleId;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.status = status;
    }
}
