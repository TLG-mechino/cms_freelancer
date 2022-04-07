package vn.compedia.website.repository;


import vn.compedia.website.dto.PostDto;
import vn.compedia.website.dto.PostSearchDto;


import java.math.BigInteger;
import java.util.List;

public interface PostRepositoryCustom {

    List<PostDto> getAllPostByUserName(String username, PostSearchDto dto);

    BigInteger countSearchRpByUserName(String username, PostSearchDto searchDto);

}
