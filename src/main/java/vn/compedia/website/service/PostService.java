package vn.compedia.website.service;

import org.springframework.stereotype.Service;
import vn.compedia.website.dto.response.PostResponseDto;
import vn.compedia.website.dto.search.PostSearchDto;

import java.util.List;

@Service
public interface PostService {

    List<PostResponseDto> getAllPostByUserName(String userName, PostSearchDto dto);
    int countSearchByUserName(String userName,PostSearchDto dto);

}
