package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotEmpty
    @NotNull
    private String username;

    @NotNull
    @NotEmpty
    private String password;

}
