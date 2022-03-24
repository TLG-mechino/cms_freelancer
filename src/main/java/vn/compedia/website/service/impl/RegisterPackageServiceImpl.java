package vn.compedia.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.compedia.website.dto.response.RegisterPackageResponseDto;
import vn.compedia.website.dto.search.RegisterPackageSearchDto;
import vn.compedia.website.repository.RegisterPackageRepository;
import vn.compedia.website.service.RegisterPackageService;

import java.util.List;

public class RegisterPackageServiceImpl implements RegisterPackageService {


    @Autowired
    RegisterPackageRepository registerPackageRepository;

    @Override
    public List<RegisterPackageResponseDto> getAllRegisterPackageByUserName(String userName, RegisterPackageSearchDto dto) {
        return registerPackageRepository.getAllRegisterPackageByUserName(userName,dto);
    }

    @Override
    public int countSearchByUser(String userName, RegisterPackageSearchDto dto) {
        return registerPackageRepository.countSearchByUserName(userName,dto);
    }
}
