package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import vn.compedia.website.dto.MarketDto;
import vn.compedia.website.dto.MarketSearchDto;
import vn.compedia.website.model.Market;
import vn.compedia.website.repository.MarketRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MarketRepositoryImpl implements MarketRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MarketDto> search(MarketSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select m.MARKET_ID, " +
                "       m.CODE, " +
                "       m.NAME, " +
                "       m.PREFIX, " +
                "       m.DESCRIPTION, " +
                "       m.STATUS, " +
                "       m.CREATE_DATE, " +
                "       m.CREATE_BY, " +
                "       m.UPDATE_DATE, " +
                "       m.UPDATE_BY ");
        appendQuery(sb, searchDto);

        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("code")) {
                sb.append(" ORDER BY m.CODE ");
            }
            if (searchDto.getSortField().equals("name")) {
                sb.append(" ORDER BY m.NAME ");
            }
            if (searchDto.getSortField().equals("prefix")) {
                sb.append(" ORDER BY m.PREFIX ");
            }
            if (searchDto.getSortField().equals("updateDate")) {
                sb.append(" ORDER BY m.UPDATE_DATE ");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY m.STATUS ");
            }
            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY m.MARKET_ID DESC ");
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
        List<MarketDto> list = new ArrayList<>();
        for (Object[] obj : resultList) {
            MarketDto dto = new MarketDto();
            dto.setMarketId(ValueUtil.getLongByObject(obj[0]));
            dto.setCode(ValueUtil.getStringByObject(obj[1]));
            dto.setName(ValueUtil.getStringByObject(obj[2]));
            dto.setPrefix(ValueUtil.getIntegerByObject(obj[3]));
            dto.setDescription(ValueUtil.getStringByObject(obj[4]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[5]));
            dto.setCreateDate(ValueUtil.getDateByObject(obj[6]));
            dto.setCreateBy(ValueUtil.getLongByObject(obj[7]));
            dto.setUpdateDate(ValueUtil.getDateByObject(obj[8]));
            dto.setUpdateBy(ValueUtil.getLongByObject(obj[9]));
            list.add(dto);
        }
        return list;
    }

    public void appendQuery(StringBuilder sb, MarketSearchDto searchDto) {
        sb.append(" from MARKET m WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            sb.append(" AND (lower(m.CODE) LIKE lower(:keyword) OR lower(m.NAME) LIKE lower(:keyword)) ");
        }
        if (searchDto.getStatus() != null) {
            sb.append(" AND m.STATUS =:status ");
        }
    }

    public Query createQuery(StringBuilder sb, MarketSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim() + "%");
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        return query;
    }

    @Override
    public BigInteger countSearch(MarketSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(m.MARKET_ID) ");
        appendQuery(sb, searchDto);
        Query query = createQuery(sb, searchDto);
        return (BigInteger) query.getSingleResult();
    }
}
