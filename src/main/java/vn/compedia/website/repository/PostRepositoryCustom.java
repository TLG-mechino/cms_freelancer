package vn.compedia.website.repository;

import vn.compedia.website.dto.response.PostResponseDto;
import vn.compedia.website.dto.search.PostSearchDto;
import vn.compedia.website.model.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<PostResponseDto> getAllPostByUserName(String userName, PostSearchDto dto);

    int countSearch (String userName,PostSearchDto dto);

}
