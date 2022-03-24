package vn.compedia.website.repository;

import vn.compedia.website.dto.entity.UserDto;
import vn.compedia.website.dto.search.UserSearchDto;
import vn.compedia.website.model.User;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface UserRepositoryCustom {

    List<UserDto> search(UserSearchDto userSearchDto);

    int countSearch(UserSearchDto userSearchDto, int type);

    UserDto findUserDtoById(Long accountId);

}
