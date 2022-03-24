package vn.compedia.website.service;

import org.springframework.stereotype.Service;
import vn.compedia.website.dto.response.ReviewResponseDto;
import vn.compedia.website.dto.search.ReviewSearchDto;

import java.util.List;


@Service
public interface ReviewService {

    List<ReviewResponseDto> getAllReviewByUserName(String userName, ReviewSearchDto reviewSearchDto);

    int countSearchByUserName(String userName, ReviewSearchDto dto);
}
