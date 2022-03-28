package vn.compedia.website.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.compedia.website.model.Review;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto extends Review {
    private String nameJob;
}
