package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountSearchDto extends BaseSearchDto {
    private String fullName;
    private String username;
    private Integer status;
    private Long accountId;
}
