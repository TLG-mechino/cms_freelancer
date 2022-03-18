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
    private String oldPassword;
    private String rePassword;
    private String newPassword;

    public AccountDto(Long accountId, String email, String username, String password, String salt, Integer status) {
        super(accountId, email, username, password, salt, status);
    }
}
