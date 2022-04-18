package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import vn.compedia.website.dto.ComplainDto;
import vn.compedia.website.dto.ComplainSearchDto;
import vn.compedia.website.repository.ComplainRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ComplainRepositoryImpl implements ComplainRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ComplainDto> search(ComplainSearchDto searchDto) {
        StringBuilder sb = new StringBuilder("");
        sb.append("SELECT c.COMPLAIN_ID, c.USERNAME, u.PHONE, c.TITLE, c.CONTENT, c.NOTE, ct.NAME as complainTypeName, c.COMPLAIN_TYPE_ID, j.NAME as jobName, c.CREATE_DATE, c.STATUS ");
        appendQuery(sb, searchDto);
        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("username")) {
                sb.append(" ORDER BY c.USERNAME ");
            }
            if (searchDto.getSortField().equals("content")) {
                sb.append(" ORDER BY c.CONTENT ");
            }
            if (searchDto.getSortField().equals("title")) {
                sb.append(" ORDER BY c.TITLE ");
            }
            if (searchDto.getSortField().equals("phone")) {
                sb.append(" ORDER BY u.PHONE ");
            }
            if (searchDto.getSortField().equals("objectName")) {
                sb.append(" ORDER BY j.NAME ");
            }
            if (searchDto.getSortField().equals("complainTypeName")) {
                sb.append(" ORDER BY ct.NAME ");
            }
            if (searchDto.getSortField().equals("note")) {
                sb.append(" ORDER BY c.NOTE ");
            }
            if (searchDto.getSortField().equals("createDate")) {
                sb.append(" ORDER BY c.CREATE_DATE ");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY c.STATUS ");
            }
            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY c.COMPLAIN_ID DESC ");
        }

        Query query = createQuery(sb, searchDto);
        if (searchDto.getPageSize() > 0) {
            query.setFirstResult(searchDto.getPageIndex());
            query.setMaxResults(searchDto.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }

        List<Object[]> result = query.getResultList();
        List<ComplainDto> complainDtos = new ArrayList<>();
        for (Object[] obj : result) {
            ComplainDto dto = new ComplainDto();
            dto.setComplainId(ValueUtil.getLongByObject(obj[0]));
            dto.setUsername(ValueUtil.getStringByObject(obj[1]));
            dto.setPhone(ValueUtil.getStringByObject(obj[2]));
            dto.setTitle(ValueUtil.getStringByObject(obj[3]));
            dto.setContent(ValueUtil.getStringByObject(obj[4]));
            dto.setNote(ValueUtil.getStringByObject(obj[5]));
            dto.setComplainTypeName(ValueUtil.getStringByObject(obj[6]));
            dto.setComplainType(ValueUtil.getIntegerByObject(obj[7]));
            dto.setObjectName(ValueUtil.getStringByObject(obj[8]));
            dto.setCreateDate(ValueUtil.getDateByObject(obj[9]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[10]));
            complainDtos.add(dto);
        }
        return complainDtos;
    }

    public Query createQuery(StringBuilder sb, ComplainSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim().toLowerCase() + "%");
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        if (searchDto.getComplainType() != null) {
            query.setParameter("complainType", searchDto.getComplainType());
        }
        return query;
    }

    public void appendQuery(StringBuilder sb, ComplainSearchDto searchDto) {
        sb.append(" FROM complain c INNER JOIN job j on c.OBJECT_ID = j.JOB_ID INNER JOIN account u on u.username = c.username " +
                " INNER JOIN complain_type ct on ct.COMPLAIN_TYPE_ID = c.COMPLAIN_TYPE_ID where 1 = 1 ");
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            sb.append(" AND (lower(c.TITLE) LIKE :keyword OR lower(c.CONTENT) LIKE :keyword OR lower(c.USERNAME) LIKE :keyword) ");
        }
        if (searchDto.getStatus() != null) {
            sb.append(" AND c.STATUS =:status ");
        }
        if (null != searchDto.getComplainType()) {
            sb.append(" AND c.complain_type_id = :complainType");
        }
    }

    ;

    @Override
    public BigInteger countSearch(ComplainSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(0) ");
        appendQuery(sb, searchDto);
        Query query = createQuery(sb, searchDto);
        return (BigInteger) query.getSingleResult();
    }
}
