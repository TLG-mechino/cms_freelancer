package vn.compedia.website.repository;

import vn.compedia.website.dto.CustomerTalkDto;
import vn.compedia.website.dto.CustomerTalkSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface CustomerTalkRepositoryCustom {
    List<CustomerTalkDto> search(CustomerTalkSearchDto searchDto);
    BigInteger countSearch(CustomerTalkSearchDto searchDto);
}
