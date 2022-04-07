package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import vn.compedia.website.dto.ExamDto;
import vn.compedia.website.dto.ExamSearchDto;
import vn.compedia.website.repository.TestRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TestRepositoryImpl implements TestRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ExamDto> search(ExamSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct e.EXAM_ID, " +
                "       e.CODE, " +
                "       e.TITLE_VN, " +
                "       e.TITLE_EN, " +
                "       e.DESCRIPTION_VN, " +
                "       e.DESCRIPTION_EN, " +
                "       e.SCORE, " +
                "       e.MAX_SCORE, " +
                "       et.NAME, " +
                "       e.STATUS, " +
                "       (select COUNT( ef.EXAM_FILE_ID) from exam_file ef WHERE e.EXAM_ID = ef.OBJECT_ID) as numberFile ");
        appendQuery(sb, searchDto);

        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("code")) {
                sb.append(" ORDER BY e.CODE ");
            }
            if (searchDto.getSortField().equals("titleVn")) {
                sb.append(" ORDER BY e.TITLE_VN ");
            }
            if (searchDto.getSortField().equals("titleEn")) {
                sb.append(" ORDER BY e.TITLE_EN ");
            }
            if (searchDto.getSortField().equals("descriptionVn")) {
                sb.append(" ORDER BY e.DESCRIPTION_VN ");
            }
            if (searchDto.getSortField().equals("descriptionEn")) {
                sb.append(" ORDER BY e.DESCRIPTION_EN ");
            }
            if (searchDto.getSortField().equals("contentVn")) {
                sb.append(" ORDER BY e.CONTENT_VN ");
            }
            if (searchDto.getSortField().equals("contentEn")) {
                sb.append(" ORDER BY e.CONTENT_EN ");
            }
            if (searchDto.getSortField().equals("score")) {
                sb.append(" ORDER BY e.SCORE ");
            }
            if (searchDto.getSortField().equals("examTypeName")) {
                sb.append(" ORDER BY et.NAME ");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY e.STATUS ");
            }
            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY e.EXAM_ID DESC ");
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
        List<ExamDto> list = new ArrayList<>();
        for (Object[] obj : resultList) {
            ExamDto dto = new ExamDto();
            dto.setExamId(ValueUtil.getLongByObject(obj[0]));
            dto.setCode(ValueUtil.getStringByObject(obj[1]));
            dto.setTitleVn(ValueUtil.getStringByObject(obj[2]));
            dto.setTitleEn(ValueUtil.getStringByObject(obj[3]));
            dto.setDescriptionVn(ValueUtil.getStringByObject(obj[4]));
            dto.setDescriptionEn(ValueUtil.getStringByObject(obj[5]));
            dto.setScore(ValueUtil.getDoubleByObject(obj[6]));
            dto.setMaxScore(ValueUtil.getDoubleByObject(obj[7]));
            dto.setExamTypeName(ValueUtil.getStringByObject(obj[8]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[9]));
            dto.setNumberFile(ValueUtil.getLongByObject(obj[10]));
            list.add(dto);
        }
        return list;
    }

    @Override
    public BigInteger countSearch(ExamSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(DISTINCT e.EXAM_ID) ");
        appendQuery(sb, searchDto);
        Query query = createQuery(sb, searchDto);
        return (BigInteger) query.getSingleResult();
    }

    public Query createQuery(StringBuilder sb, ExamSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim() + "%");
        }
        if (searchDto.getExamTypeId() != null) {
            query.setParameter("examTypeId", searchDto.getExamTypeId());
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        return query;

    }

    public void appendQuery(StringBuilder sb, ExamSearchDto searchDto) {
        sb.append(" from exam e LEFT JOIN exam_type et ON e.EXAM_TYPE_ID = et.EXAM_TYPE_ID  " +
                " LEFT JOIN exam_file ef ON e.EXAM_ID = ef.OBJECT_ID WHERE 1 = 1 ");
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            sb.append(" AND (lower(e.CODE) LIKE lower(:keyword) " +
                    "OR lower(e.TITLE_VN) LIKE lower(:keyword)" +
                    "OR lower(e.TITLE_EN) LIKE lower(:keyword)" +
                    "OR lower(e.DESCRIPTION_VN) LIKE lower(:keyword)" +
                    "OR lower(e.DESCRIPTION_EN) LIKE lower(:keyword)" +
                    "OR lower(e.CONTENT_VN) LIKE lower(:keyword)" +
                    "OR lower(e.CONTENT_EN) LIKE lower(:keyword))");
        }
        if (searchDto.getExamTypeId() != null) {
            sb.append(" AND e.EXAM_TYPE_ID =:examTypeId ");
        }
        if (searchDto.getStatus() != null) {
            sb.append(" AND e.STATUS =:status ");
        }
    }
}
