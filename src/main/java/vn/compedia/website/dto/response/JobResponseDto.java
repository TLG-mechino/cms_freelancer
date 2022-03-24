package vn.compedia.website.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class JobResponseDto {
    private Long id;
    private String codeJob;
    private String name;
    private String description;
    private String userName;
    private String startTime;
    private String endTime;
    private Double moneyFrom;
    private Double moneyTo;
    private String createTime;
    private String modifiedTime;
    private Integer status;
}
