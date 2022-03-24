package vn.compedia.website.repository;

import org.springframework.data.domain.Pageable;
import vn.compedia.website.dto.response.ReviewResponseDto;
import vn.compedia.website.dto.search.ReviewSearchDto;

import java.util.List;


public interface ReviewRepositoryCustom {

    List<ReviewResponseDto> getAllReviewByUserName(String userName, ReviewSearchDto reviewSearchDto);
    int countSearchByUserName (String userName,ReviewSearchDto dto);
}
