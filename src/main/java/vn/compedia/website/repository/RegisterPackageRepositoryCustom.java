package vn.compedia.website.repository;

import org.springframework.data.domain.Pageable;
import vn.compedia.website.dto.response.RegisterPackageResponseDto;
import vn.compedia.website.model.RegisterPackage;

import java.util.List;

public interface RegisterPackageRepositoryCustom {

    List<RegisterPackageResponseDto> getAllRegisterPackageByUserName(String userName, Pageable pageable);
}
