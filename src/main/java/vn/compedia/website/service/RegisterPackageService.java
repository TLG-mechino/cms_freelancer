package vn.compedia.website.service;

import org.springframework.stereotype.Service;
import vn.compedia.website.dto.response.RegisterPackageResponseDto;
import vn.compedia.website.dto.search.RegisterPackageSearchDto;

import java.util.List;

@Service
public interface RegisterPackageService {
    List<RegisterPackageResponseDto> getAllRegisterPackageByUserName(String userName, RegisterPackageSearchDto dto);
    int countSearchByUser (String userName,RegisterPackageSearchDto dto);
}
