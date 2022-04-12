package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.CustomerTalk;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTalkDto extends CustomerTalk {
    private Integer stt;
}
