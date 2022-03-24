package vn.compedia.website.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String content;
    private String userName;
    private String postingTime;
    private Integer blockComment;
    private Integer status;
    private String filePost;
}
