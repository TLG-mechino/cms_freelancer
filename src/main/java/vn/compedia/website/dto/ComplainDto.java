package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Complain;

@Getter
@Setter

public class ComplainDto extends Complain {
    private String phone;
    private String complainTypeName;
}
