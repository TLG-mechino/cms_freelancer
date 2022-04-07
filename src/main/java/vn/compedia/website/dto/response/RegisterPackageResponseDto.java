package vn.compedia.website.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterPackageResponseDto {
    private Long registerId;
    private String username;
    private String registrationTime;
    private String expiredTime;
    private Double money;
    private String namePackage;
    private Integer status;
}
