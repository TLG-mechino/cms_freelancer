package vn.compedia.website.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto {

    private Long id;
    private String username;
    private Long jobId;
    private String content;
    private Integer starAmount;
    private Integer status;
    private String reviewTime;
    private String nameJob;
    private String title;

}
