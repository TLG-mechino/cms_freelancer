package vn.compedia.website.repository;

import org.springframework.data.domain.Pageable;
import vn.compedia.website.dto.response.ReviewResponseDto;

import java.util.List;


public interface ReviewRepositoryCustom {

    List<ReviewResponseDto> getAllReviewByUserName(String userName, Pageable pageable);
}
