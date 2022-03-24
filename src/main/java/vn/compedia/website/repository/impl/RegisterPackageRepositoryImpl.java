package vn.compedia.website.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.response.RegisterPackageResponseDto;
import vn.compedia.website.dto.search.RegisterPackageSearchDto;
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
    public List<RegisterPackageResponseDto> getAllRegisterPackageByUserName(String userName, RegisterPackageSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rp.REGISTER_PACKAGE_ID," +
                "       rp.USERNAME," +
                "       rp.REGISTRATION_TIME," +
                "       rp.EXPIRED_TIME," +
                "       rp.MONEY," +
                "       result1.PACKAGE_SERVICE_ID," +
                "       result1.NAME," +
                "       rp.STATUS ");
        appendQueryByUserName(sb,searchDto);
        Query query = createQueryByUser(sb,userName,searchDto);
        if (searchDto.getPageSize() > 0) {
            query.setFirstResult(searchDto.getPageIndex() * searchDto.getPageSize());
            query.setMaxResults(searchDto.getPageSize());
        }
        else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<RegisterPackageResponseDto> dtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                RegisterPackageResponseDto responseDto = new RegisterPackageResponseDto();
                responseDto.setRegisterId(ValueUtil.getLongByObject(obj[0]));
                responseDto.setUserName(ValueUtil.getStringByObject(obj[1]));
                if (obj[2] != null) {
                    responseDto.setRegistrationTime(DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[2]), DateUtil.DATE_FORMAT));
                }
                if (obj[3] != null) {
                    responseDto.setExpiredTime(DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[3]), DateUtil.DATE_FORMAT));
                }
                responseDto.setMoney(ValueUtil.getDoubleByObject(obj[4]));
                responseDto.setNamePackage(ValueUtil.getStringByObject(obj[6]));
                responseDto.setStatus(ValueUtil.getIntegerByObject(obj[7]));
                dtos.add(responseDto);
            }
        }
        return dtos;
    }


    private Query createQueryByUser(StringBuilder sb,String userName , RegisterPackageSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName",userName);
        if (searchDto.getKeyword() != null) {
            query.setParameter("keyword","%"+searchDto.getKeyword().trim()+"%");
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status",searchDto.getStatus());
        }
        if (searchDto.getMoneyPackageService() != null ) {
            query.setParameter("money",searchDto.getMoneyPackageService());
        }
        return query;
    }


    private void appendQueryByUserName (StringBuilder sb , RegisterPackageSearchDto dto) {
        sb.append(" from register_package rp " +
                "         inner join (select ps.NAME,ps.CODE,ps.PACKAGE_SERVICE_ID from package_service ps) result1 " +
                "                    on rp.PACKAGE_SERVICE_ID = result1.PACKAGE_SERVICE_ID" +
                " where rp.USERNAME=:userName ");
        if (dto.getKeyword() != null) {
            sb.append(" and result1.NAME like :keyword ");
        }
        if (dto.getStatus() != null) {
            sb.append(" and rp.STATUS =:status ");
        }
        if (dto.getMoneyPackageService() != null) {
            sb.append(" and rp.MONEY =:money");
        }
    }

    @Override
    public int countSearchByUserName(String userName, RegisterPackageSearchDto dto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select COUNT(rp.REGISTER_PACKAGE_ID) ");
        appendQueryByUserName(sb,dto);
        Query query = createQueryByUser(sb,userName,dto);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }
}
