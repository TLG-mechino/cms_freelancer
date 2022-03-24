package vn.compedia.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.compedia.website.dto.response.PostResponseDto;
import vn.compedia.website.dto.search.PostSearchDto;
import vn.compedia.website.repository.PostRepository;
import vn.compedia.website.repository.impl.PostRepositoryImpl;
import vn.compedia.website.service.PostService;

import java.util.List;

public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    public List<PostResponseDto> getAllPostByUserName(String userName, PostSearchDto dto) {
        return postRepository.getAllPostByUserName(userName,dto);
    }

    @Override
    public int countSearchByUserName(String userName, PostSearchDto dto) {
        return postRepository.countSearch(userName,dto);
    }
}
