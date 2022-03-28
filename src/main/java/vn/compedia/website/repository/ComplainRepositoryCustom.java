package vn.compedia.website.repository;

import vn.compedia.website.dto.ComplainDto;
import vn.compedia.website.dto.ComplainSearchDto;
import vn.compedia.website.dto.HashtagDto;
import vn.compedia.website.dto.HashtagSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface ComplainRepositoryCustom {

    List<ComplainDto> search(ComplainSearchDto searchDto);

    BigInteger countSearch(ComplainSearchDto searchDto);

}
