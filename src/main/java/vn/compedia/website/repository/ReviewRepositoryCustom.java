package vn.compedia.website.repository;

import vn.compedia.website.dto.ReviewDto;
import vn.compedia.website.dto.ReviewSearchDto;

import java.math.BigInteger;
import java.util.List;


public interface ReviewRepositoryCustom {

    List<ReviewDto> getAllReviewByUserName(String username, ReviewSearchDto reviewSearchDto);

    BigInteger countSearchByUserName (String username, ReviewSearchDto dto);
}
