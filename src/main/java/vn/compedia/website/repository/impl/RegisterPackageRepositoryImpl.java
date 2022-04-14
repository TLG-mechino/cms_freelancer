package vn.compedia.website.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.PackageServiceDto;
import vn.compedia.website.dto.PackageServiceSearchDto;
import vn.compedia.website.dto.response.RegisterPackageResponseDto;
import vn.compedia.website.dto.search.RegisterPackageSearchDto;
import vn.compedia.website.repository.RegisterPackageRepositoryCustom;
import vn.compedia.website.util.DateUtil;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RegisterPackageRepositoryImpl implements RegisterPackageRepositoryCustom {

    @Autowired
    EntityManager entityManager;


    @Override
    public List<PackageServiceDto> getAllRegisterPackageByUserName(String username, PackageServiceSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rp.REGISTER_PACKAGE_ID," +
                "       rp.USERNAME," +
                "       rp.REGISTRATION_TIME," +
                "       rp.EXPIRED_TIME," +
                "       rp.MONEY," +
                "       result1.PACKAGE_SERVICE_ID," +
                "       result1.NAME_VN," +
                "       rp.STATUS ");
        appendQueryByUserName(sb,searchDto);
        Query query = createQueryByUser(sb,username,searchDto);
        if (searchDto.getPageSize() > 0) {
            query.setFirstResult(searchDto.getPageIndex() * searchDto.getPageSize());
            query.setMaxResults(searchDto.getPageSize());
        }
        else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<PackageServiceDto> dtos = new ArrayList<>();
        List<Object[]> result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                PackageServiceDto responseDto = new PackageServiceDto();
                responseDto.setPackageServiceId(ValueUtil.getLongByObject(obj[0]));
                responseDto.setUsername(ValueUtil.getStringByObject(obj[1]));
                responseDto.setRegistrationTime(ValueUtil.getDateByObject(obj[2]));
                responseDto.setExpiredTime(ValueUtil.getDateByObject(obj[3]));
                responseDto.setMoney(ValueUtil.getDoubleByObject(obj[4]));
                responseDto.setNamePackage(ValueUtil.getStringByObject(obj[6]));
                responseDto.setStatus(ValueUtil.getIntegerByObject(obj[7]));
                dtos.add(responseDto);
            }
        }
        return dtos;
    }

    private Query createQueryByUser(StringBuilder sb,String username , PackageServiceSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("username",username);
        if (searchDto.getKeyword() != null) {
            query.setParameter("keyword","%"+searchDto.getKeyword().trim()+"%");
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status",searchDto.getStatus());
        }
        if (searchDto.getMoney() != null ) {
            query.setParameter("money",searchDto.getMoney());
        }
        return query;
    }

    private void appendQueryByUserName (StringBuilder sb , PackageServiceSearchDto dto) {
        sb.append(" from register_package rp " +
                "         inner join (select ps.NAME_VN,ps.CODE,ps.PACKAGE_SERVICE_ID from package_service ps) result1 " +
                "                    on rp.PACKAGE_SERVICE_ID = result1.PACKAGE_SERVICE_ID" +
                " where rp.USERNAME = :username ");
        if (dto.getKeyword() != null) {
            sb.append(" and result1.NAME_VN like :keyword ");
        }
        if (dto.getStatus() != null) {
            sb.append(" and rp.STATUS =:status ");
        }
        if (dto.getMoney() != null) {
            sb.append(" and rp.MONEY =:money");
        }
    }

    @Override
    public BigInteger countSearchByUserName(String username, PackageServiceSearchDto dto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select COUNT(rp.REGISTER_PACKAGE_ID) ");
        appendQueryByUserName(sb,dto);
        Query query = createQueryByUser(sb,username,dto);
        return (BigInteger) query.getSingleResult();
    }
}
