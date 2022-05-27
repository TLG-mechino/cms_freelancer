package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import vn.compedia.website.dto.CustomerTalkDto;
import vn.compedia.website.dto.CustomerTalkSearchDto;
import vn.compedia.website.dto.HashtagDto;
import vn.compedia.website.dto.HashtagSearchDto;
import vn.compedia.website.repository.CustomerTalkRepositoryCustom;
import vn.compedia.website.util.PropertiesUtil;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CustomerTalkRepositoryImpl implements CustomerTalkRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CustomerTalkDto> search(CustomerTalkSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ct.customer_talk_id, " +
                "       ct.position_vn, " +
                "       ct.position_en, " +
                "       ct.full_name, " +
                "       ct.content_vn, " +
                "       ct.content_en, " +
                "       ct.image_path, " +
                "       ct.status ");
        appendQuery(sb, searchDto);

        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("position")) {
                sb.append(" ORDER BY ct.position ");
            }
            if (searchDto.getSortField().equals("fullName")) {
                sb.append(" ORDER BY ct.full_name ");
            }
            if (searchDto.getSortField().equals("content")) {
                sb.append(" ORDER BY ct.content ");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY ct.status ");
            }

            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY ct.customer_talk_id DESC ");
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
        List<CustomerTalkDto> list = new ArrayList<>();
        for (Object[] obj : resultList) {
            CustomerTalkDto dto = new CustomerTalkDto();
            dto.setCustomerTalkId(ValueUtil.getLongByObject(obj[0]));
            dto.setPositionVn(ValueUtil.getStringByObject(obj[1]));
            dto.setPositionEn(ValueUtil.getStringByObject(obj[2]));
            dto.setFullName(ValueUtil.getStringByObject(obj[3]));
            dto.setContentVn(ValueUtil.getStringByObject(obj[4]));
            dto.setContentEn(ValueUtil.getStringByObject(obj[5]));
            dto.setImagePath(PropertiesUtil.getProperty("vn.compedia.static.context") + ValueUtil.getStringByObject(obj[6]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[7]));
            list.add(dto);
        }
        return list;
    }

    @Override
    public BigInteger countSearch(CustomerTalkSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(ct.customer_talk_id) ");
        appendQuery(sb, searchDto);
        Query query = createQuery(sb, searchDto);
        return (BigInteger) query.getSingleResult();
    }

    public Query createQuery(StringBuilder sb, CustomerTalkSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim() + "%");
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        return query;

    }

    public void appendQuery(StringBuilder sb, CustomerTalkSearchDto searchDto) {
        sb.append(" from customer_talk ct WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            sb.append(" AND (lower(ct.position) LIKE lower(:keyword) " +
                    " OR lower(ct.full_name) LIKE lower(:keyword))");
        }
        if (searchDto.getStatus() != null) {
            sb.append(" AND ct.status =:status ");
        }
    }
}
