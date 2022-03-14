package vn.compedia.website.repository;

import vn.compedia.website.dto.MarketDto;
import vn.compedia.website.dto.MarketSearchDto;
import vn.compedia.website.model.Market;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public interface MarketRepositoryCustom {

    List<MarketDto> search(MarketSearchDto searchDto);

    BigInteger countSearch(MarketSearchDto searchDto);
}

