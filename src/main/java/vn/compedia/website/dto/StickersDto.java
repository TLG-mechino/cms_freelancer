package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Sticker;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StickersDto extends Sticker {
    private Long stt;
}
