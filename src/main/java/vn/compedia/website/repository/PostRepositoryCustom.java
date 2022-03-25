package vn.compedia.website.repository;


import vn.compedia.website.dto.PostDto;
import vn.compedia.website.dto.PostSearchDto;


import java.math.BigInteger;
import java.util.List;

public interface PostRepositoryCustom {
    List<PostDto> getAllPostByUserName(String userName, PostSearchDto dto);

    BigInteger countSearchRpByUserName(String userName, PostSearchDto searchDto);

}
