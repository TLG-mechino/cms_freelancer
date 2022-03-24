package vn.compedia.website.repository.config;


import vn.compedia.website.dto.config.ServiceConfigDto;
import vn.compedia.website.dto.config.ServiceConfigSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface ServiceConfigRepositoryCustom {
    List<ServiceConfigDto> search(ServiceConfigSearchDto searchDto);

    BigInteger countSearch(ServiceConfigSearchDto searchDto);
}
