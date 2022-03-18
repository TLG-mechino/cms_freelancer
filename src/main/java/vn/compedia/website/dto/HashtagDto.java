package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Hashtag;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HashtagDto extends Hashtag {
    private Long stt;
}
