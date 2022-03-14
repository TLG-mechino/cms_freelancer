package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Account;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto extends Account {
    private String rePassword;
    private String roleName;

    public AccountDto(Long accountId, Long roleId, String fullName, String email, String username, String password, String salt, Integer status, String roleName) {
        super(accountId, roleId, fullName, email, username, password, salt, status);
        this.roleName = roleName;
    }
}
