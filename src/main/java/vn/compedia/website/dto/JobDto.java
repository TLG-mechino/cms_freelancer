package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Job;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobDto extends Job {
    private Long stt;
    private String hashtagTitle;
}
