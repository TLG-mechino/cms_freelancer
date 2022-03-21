package vn.compedia.website.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.response.RegisterPackageResponseDto;
import vn.compedia.website.repository.RegisterPackageRepositoryCustom;
import vn.compedia.website.util.DateUtil;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class RegisterPackageRepositoryImpl implements RegisterPackageRepositoryCustom {

    @Autowired
    EntityManager entityManager;


    @Override
    public List<RegisterPackageResponseDto> getAllRegisterPackageByUserName(String userName, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select result1.REGISTER_PACKAGE_ID," +
                "       result1.USERNAME," +
                "       result1.REGISTRATION_TIME," +
                "       result1.EXPIRED_TIME," +
                "       ps.MONEY," +
                "       result1.PACKAGE_SERVICE_ID," +
                "       ps.NAME" +
                " from package_service ps" +
                "         inner join (select * from register_package repackage where repackage.USERNAME = :userName) result1" +
                "                    on ps.PACKAGE_SERVICE_ID = result1.PACKAGE_SERVICE_ID");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName", userName);
        int positionStart = pageable.getPageNumber() * pageable.getPageSize();
        query.setFirstResult(positionStart);
        query.setMaxResults(pageable.getPageSize());
        List<RegisterPackageResponseDto> dtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                RegisterPackageResponseDto responseDto = new RegisterPackageResponseDto();
                responseDto.setRegisterId(null == ValueUtil.getLongByObject(obj[0]) ? 0L : ValueUtil.getLongByObject(obj[0]));
                responseDto.setUserName(null == ValueUtil.getStringByObject(obj[1]) ? "" : ValueUtil.getStringByObject(obj[1]));
                if (obj[2] != null) {
                    responseDto.setRegistrationTime(DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[2]), DateUtil.DATE_FORMAT));
                }
                if (obj[3] != null) {
                    responseDto.setExpiredTime(DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[3]), DateUtil.DATE_FORMAT));
                }
                responseDto.setMoney(null == ValueUtil.getDoubleByObject(obj[4]) ? null : ValueUtil.getDoubleByObject(obj[4]));
                responseDto.setNamePackage(null == ValueUtil.getStringByObject(obj[6]) ? null : ValueUtil.getStringByObject(obj[6]));
                dtos.add(responseDto);
            }
        }
        return dtos;
    }
}
