package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Post;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto extends Post {
    private Long stt;
    private Long numberFile;
    private String filePost;
}
