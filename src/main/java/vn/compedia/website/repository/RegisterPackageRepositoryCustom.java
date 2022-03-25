package vn.compedia.website.repository;

import org.springframework.data.domain.Pageable;
import vn.compedia.website.dto.PackageServiceDto;
import vn.compedia.website.dto.PackageServiceSearchDto;
import vn.compedia.website.dto.response.RegisterPackageResponseDto;
import vn.compedia.website.dto.search.RegisterPackageSearchDto;
import vn.compedia.website.model.RegisterPackage;

import java.math.BigInteger;
import java.util.List;

public interface RegisterPackageRepositoryCustom {

    List<PackageServiceDto> getAllRegisterPackageByUserName(String userName, PackageServiceSearchDto dto);
    BigInteger countSearchByUserName(String userName, PackageServiceSearchDto dto);
}
