package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Market;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MarketDto extends Market {
    private int stt;
    private String textCreateDate;
    private String textStatus;
}
