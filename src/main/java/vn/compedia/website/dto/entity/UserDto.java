package vn.compedia.website.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.User;

@Getter
@Setter
public class UserDto extends User {
    private Long accountId;
    private String fullName;
    private String phone;
    private String email;
    private String createDate;
    private Integer status;
}
