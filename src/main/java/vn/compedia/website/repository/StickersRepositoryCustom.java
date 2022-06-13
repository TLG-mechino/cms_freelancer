package vn.compedia.website.repository;

import vn.compedia.website.dto.HashtagDto;
import vn.compedia.website.dto.HashtagSearchDto;
import vn.compedia.website.dto.StickerSearchDto;
import vn.compedia.website.dto.StickersDto;

import java.math.BigInteger;
import java.util.List;

public interface StickersRepositoryCustom {
    List<StickersDto> search(StickerSearchDto searchDto);

    BigInteger countSearch(StickerSearchDto searchDto);
}
