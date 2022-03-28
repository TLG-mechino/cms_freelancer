package vn.compedia.website.repository;

import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.dto.AccountSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface AccountRepositoryCustom {

    boolean checkExistEmail(String email);

    List<AccountDto> search(AccountSearchDto searchDto, Integer type);

    BigInteger countSearch(AccountSearchDto searchDto, Integer type);

}
