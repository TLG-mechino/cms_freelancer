package vn.compedia.website.repository;

import vn.compedia.website.dto.entity.UserDto;
import vn.compedia.website.dto.search.UserSearchDto;
import vn.compedia.website.model.User;

import java.util.List;

public interface UserRepositoryCustom {

    List<UserDto> search(UserSearchDto userSearchDto);


}
