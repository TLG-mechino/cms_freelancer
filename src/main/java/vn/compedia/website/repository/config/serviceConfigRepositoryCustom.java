package vn.compedia.website.repository.config;



import vn.compedia.website.dto.config.serviceConfigDto;
import vn.compedia.website.dto.config.serviceConfigSearchDto;
import vn.compedia.website.model.PackageService;

import java.math.BigInteger;
import java.util.List;

public interface serviceConfigRepositoryCustom {
    List<PackageService> search(serviceConfigSearchDto searchDto);

    BigInteger countSearch(serviceConfigSearchDto searchDto);
}
