package vn.compedia.website.repository;

import vn.compedia.website.dto.HashtagDto;
import vn.compedia.website.dto.HashtagSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface HashtagRepositoryCustom {
    List<HashtagDto> search(HashtagSearchDto searchDto);

    BigInteger countSearch(HashtagSearchDto searchDto);
}
