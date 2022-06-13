package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import vn.compedia.website.dto.HashtagDto;
import vn.compedia.website.dto.HashtagSearchDto;
import vn.compedia.website.dto.StickerSearchDto;
import vn.compedia.website.dto.StickersDto;
import vn.compedia.website.repository.StickersRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class StickersRepositoryCustomImpl implements StickersRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<StickersDto> search(StickerSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select h.STICKERS_ID, " +
                "       h.CODE, " +
                "       h.NAME_VN, " +
                "       h.NAME_EN, " +
                "       h.DESCRIPTION_VN, " +
                "       h.DESCRIPTION_EN, " +
                "       h.STATUS, " +
                "       h.CREATE_DATE, " +
                "       h.CREATE_BY, " +
                "       h.UPDATE_DATE, " +
                "       h.UPDATE_BY, " +
                "       h.POSITION, " +
                "       h.COLOR, " +
                "       h.MONEY ");
        appendQuery(sb, searchDto);

        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("code")) {
                sb.append(" ORDER BY h.CODE ");
            }
            if (searchDto.getSortField().equals("nameVn")) {
                sb.append(" ORDER BY h.NAME_VN ");
            }
            if (searchDto.getSortField().equals("nameEn")) {
                sb.append(" ORDER BY h.NAME_EN ");
            }
            if (searchDto.getSortField().equals("descriptionVn")) {
                sb.append(" ORDER BY h.DESCRIPTION_VN ");
            }
            if (searchDto.getSortField().equals("descriptionEn")) {
                sb.append(" ORDER BY h.DESCRIPTION_EN ");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY h.STATUS ");
            }
            if (searchDto.getSortField().equals("createBy")) {
                sb.append(" ORDER BY h.CREATE_BY ");
            }
            if (searchDto.getSortField().equals("createDate")) {
                sb.append(" ORDER BY h.CREATE_DATE ");
            }
            if (searchDto.getSortField().equals("money")){
                sb.append(" ORDER BY h.MONEY");
            }

            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY h.STICKERS_ID DESC ");
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
        List<StickersDto> list = new ArrayList<>();
        for (Object[] obj : resultList) {
            StickersDto dto = new StickersDto();
            dto.setStickersId(ValueUtil.getLongByObject(obj[0]));
            dto.setCode(ValueUtil.getStringByObject(obj[1]));
            dto.setNameVn(ValueUtil.getStringByObject(obj[2]));
            dto.setNameEn(ValueUtil.getStringByObject(obj[3]));
            dto.setDescriptionVn(ValueUtil.getStringByObject(obj[4]));
            dto.setDescriptionEn(ValueUtil.getStringByObject(obj[5]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[6]));
            dto.setCreateDate(ValueUtil.getDateByObject(obj[7]));
            dto.setCreateBy(ValueUtil.getStringByObject(obj[8]));
            dto.setUpdateDate(ValueUtil.getDateByObject(obj[9]));
            dto.setUpdateBy(ValueUtil.getStringByObject(obj[10]));
            dto.setPosition(ValueUtil.getIntegerByObject(obj[11]));
            dto.setColor(ValueUtil.getStringByObject(obj[12]));
            dto.setMoney(ValueUtil.getDoubleByObject(obj[13]));
            list.add(dto);
        }
        return list;
    }

    @Override
    public BigInteger countSearch(StickerSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(h.STICKERS_ID) ");
        appendQuery(sb, searchDto);
        Query query = createQuery(sb, searchDto);
        return (BigInteger) query.getSingleResult();
    }

    public Query createQuery(StringBuilder sb, StickerSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim() + "%");
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        return query;

    }

    public void appendQuery(StringBuilder sb, StickerSearchDto searchDto) {
        sb.append(" from stickers h WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            sb.append(" AND (lower(h.CODE) LIKE lower(:keyword) " +
                    "OR lower(h.NAME_VN) LIKE lower(:keyword) " +
                    "OR lower(h.NAME_EN) LIKE lower(:keyword) " +
                    "OR lower(h.DESCRIPTION_VN) LIKE lower(:keyword) " +
                    "OR lower(h.DESCRIPTION_EN) LIKE lower(:keyword) " +
                    "OR lower(h.CREATE_BY) LIKE lower(:keyword))");
        }
        if (searchDto.getStatus() != null) {
            sb.append(" AND h.STATUS =:status ");
        }
    }
}
