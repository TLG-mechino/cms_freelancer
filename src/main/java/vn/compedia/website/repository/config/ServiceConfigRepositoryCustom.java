package vn.compedia.website.repository.config;


import vn.compedia.website.dto.config.ServiceConfigSearchDto;
import vn.compedia.website.model.PackageService;

import java.math.BigInteger;
import java.util.List;

public interface ServiceConfigRepositoryCustom {
    List<PackageService> search(ServiceConfigSearchDto searchDto);

    BigInteger countSearch(ServiceConfigSearchDto searchDto);
}
