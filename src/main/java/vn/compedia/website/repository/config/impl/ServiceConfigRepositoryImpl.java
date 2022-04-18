package vn.compedia.website.repository.config.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import vn.compedia.website.dto.config.ServiceConfigDto;
import vn.compedia.website.dto.config.ServiceConfigSearchDto;
import vn.compedia.website.repository.config.ServiceConfigRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ServiceConfigRepositoryImpl implements ServiceConfigRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ServiceConfigDto> search(ServiceConfigSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select s.PACKAGE_SERVICE_ID, " +
                "       s.CODE, " +
                "       s.NAME_VN, " +
                "       s.NAME_EN, " +
                "       s.LIMIT_POST, " +
                "       s.LIMIT_COMMENT, " +
                "       s.LIMIT_SHOW, " +
                "       s.LIMIT_TRANSACTION, " +
                "       s.POST, " +
                "       s.COMMENT, " +
                "       s.SHOW, " +
                "       s.TRANSACTION, " +
                "       s.MONEY, " +
                "       s.DESCRIPTION_VN, " +
                "       s.DESCRIPTION_EN, " +
                "       s.STATUS, " +
                "       s.USERNAME, " +
                "       s.CREATE_DATE, " +
                "       s.UPDATE_DATE, " +
                "       s.UPDATE_BY, " +
                "       s.COLOR ");
        appendQuery(sb, searchDto);

        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("code")) {
                sb.append(" ORDER BY s.CODE ");
            }
            if (searchDto.getSortField().equals("name")) {
                sb.append(" ORDER BY s.NAME ");
            }
            if (searchDto.getSortField().equals("money")) {
                sb.append(" ORDER BY s.MONEY ");
            }
            if (searchDto.getSortField().equals("description")) {
                sb.append(" ORDER BY s.DESCRIPTION ");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY s.STATUS ");
            }
            if (searchDto.getSortField().equals("serviceTypeName")) {
                sb.append(" ORDER BY st.NAME ");
            }
            if (searchDto.getSortField().equals("username")) {
                sb.append(" ORDER BY s.USERNAME ");
            }
            if (searchDto.getSortField().equals("createDate")) {
                sb.append(" ORDER BY s.CREATE_DATE ");
            }

            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY s.PACKAGE_SERVICE_ID DESC ");
        }

        Query query = createQuery(sb, searchDto);
        if (searchDto.getPageSize() > 0) {
            query.setFirstResult(searchDto.getPageIndex());
            query.setMaxResults(searchDto.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<Object[]> resultList = query.getResultList();
        List<ServiceConfigDto> list = new ArrayList<>();
        for (Object[] obj : resultList) {
            ServiceConfigDto dto = new ServiceConfigDto();
            dto.setPackageServiceId(ValueUtil.getLongByObject(obj[0]));
            dto.setCode(ValueUtil.getStringByObject(obj[1]));
            dto.setNameVn(ValueUtil.getStringByObject(obj[2]));
            dto.setNameEn(ValueUtil.getStringByObject(obj[3]));
            dto.setLimitPost(ValueUtil.getBooleanByObject(obj[4]));
            dto.setLimitComment(ValueUtil.getBooleanByObject(obj[5]));
            dto.setLimitShow(ValueUtil.getBooleanByObject(obj[6]));
            dto.setLimitTransaction(ValueUtil.getBooleanByObject(obj[7]));
            dto.setPost(ValueUtil.getIntegerByObject(obj[8]));
            dto.setComment(ValueUtil.getIntegerByObject(obj[9]));
            dto.setShow(ValueUtil.getIntegerByObject(obj[10]));
            dto.setTransaction(ValueUtil.getIntegerByObject(obj[11]));
            dto.setMoney(ValueUtil.getDoubleByObject(obj[12]));
            dto.setDescriptionVn(ValueUtil.getStringByObject(obj[13]));
            dto.setDescriptionEn(ValueUtil.getStringByObject(obj[14]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[15]));
            dto.setUsername(ValueUtil.getStringByObject(obj[16]));
            dto.setCreateDate(ValueUtil.getDateByObject(obj[17]));
            dto.setUpdateDate(ValueUtil.getDateByObject(obj[18]));
            dto.setUpdateBy(ValueUtil.getStringByObject(obj[19]));
            dto.setColor(ValueUtil.getStringByObject(obj[20]));
            list.add(dto);
        }
        return list;
    }

    @Override
    public BigInteger countSearch(ServiceConfigSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(s.PACKAGE_SERVICE_ID) ");
        appendQuery(sb, searchDto);
        Query query = createQuery(sb, searchDto);
        return (BigInteger) query.getSingleResult();
    }

    public Query createQuery(StringBuilder sb, ServiceConfigSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim() + "%");
        }
        if (searchDto.getFillServiceType() != null) {
            query.setParameter("fillServiceType", searchDto.getFillServiceType());
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        return query;

    }

    public void appendQuery(StringBuilder sb, ServiceConfigSearchDto searchDto) {
        sb.append(" from package_service s WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            sb.append(" AND (lower(s.CODE) LIKE lower(:keyword) " +
                    "OR lower(s.NAME_VN) LIKE lower(:keyword) " +
                    "OR lower(s.NAME_EN) LIKE lower(:keyword))");
        }
        if (searchDto.getStatus() != null) {
            sb.append(" AND s.STATUS =:status ");
        }
    }
}
