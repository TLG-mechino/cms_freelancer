package vn.compedia.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.compedia.website.dto.response.ReviewResponseDto;
import vn.compedia.website.dto.search.ReviewSearchDto;
import vn.compedia.website.repository.ReviewRepository;
import vn.compedia.website.service.ReviewService;

import java.util.List;

public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Override
    public List<ReviewResponseDto> getAllReviewByUserName(String userName, ReviewSearchDto reviewSearchDto) {
        return reviewRepository.getAllReviewByUserName(userName,reviewSearchDto);
    }

    @Override
    public int countSearchByUserName(String userName, ReviewSearchDto dto) {
        return reviewRepository.countSearchByUserName(userName,dto);
    }
}
