package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.ExamDto;
import vn.compedia.website.dto.UserExamDto;
import vn.compedia.website.dto.UserExamSearchDto;
import vn.compedia.website.repository.TestEvaluateRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TestEvaluateRepositoryImpl implements TestEvaluateRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserExamDto> search(UserExamSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select distinct ue.USER_EXAM_ID, " +
                "       ue.USERNAME, " +
                "       e.CODE, " +
                "       ue.SUBMIT_TIME, " +
                "       ue.SCORE, " +
                "       ue.NOTE, " +
                "       ue.STATUS, " +
                "       (select COUNT(ef.EXAM_FILE_ID) from exam_file ef WHERE ue.USER_EXAM_ID = ef.OBJECT_ID AND TYPE = 2) as numberFile ");
        appendQuery(sb, searchDto);

        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("username")) {
                sb.append(" ORDER BY ue.USERNAME ");
            }
            if (searchDto.getSortField().equals("examCode")) {
                sb.append(" ORDER BY e.CODE ");
            }
            if (searchDto.getSortField().equals("submitTime")) {
                sb.append(" ORDER BY ue.SUBMIT_TIME ");
            }
            if (searchDto.getSortField().equals("score")) {
                sb.append(" ORDER BY ue.SCORE ");
            }
            if (searchDto.getSortField().equals("numberFile")) {
                sb.append(" ORDER BY numberFile ");
            }
            if (searchDto.getSortField().equals("note")) {
                sb.append(" ORDER BY ue.NOTE ");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY ue.STATUS ");
            }
            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY ue.USER_EXAM_ID DESC ");
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
        List<UserExamDto> list = new ArrayList<>();
        for (Object[] obj : resultList) {
            UserExamDto dto = new UserExamDto();
            dto.setUserExamId(ValueUtil.getLongByObject(obj[0]));
            dto.setUsername(ValueUtil.getStringByObject(obj[1]));
            dto.setExamCode(ValueUtil.getStringByObject(obj[2]));
            dto.setSubmitTime(ValueUtil.getDateByObject(obj[3]));
            dto.setScore(ValueUtil.getDoubleByObject(obj[4]));
            dto.setNote(ValueUtil.getStringByObject(obj[5]));
            dto.setStatus(ValueUtil.getIntegerByObject(obj[6]));
            dto.setNumberFile(ValueUtil.getLongByObject(obj[7]));
            list.add(dto);
        }
        return list;
    }

    @Override
    public BigInteger countSearch(UserExamSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(DISTINCT ue.USER_EXAM_ID) ");
        appendQuery(sb, searchDto);
        Query query = createQuery(sb, searchDto);
        return (BigInteger) query.getSingleResult();
    }

    @Override
    public UserExamDto findByIdAndExamCode(Long userExamId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ue.USER_EXAM_ID, " +
                "       ue.USERNAME, " +
                "       e.EXAM_ID, "  +
                "       e.CODE, " +
                "       ue.SUBMIT_TIME, " +
                "       ue.SCORE, " +
                "       ue.NOTE, " +
                "       ue.STATUS, " +
                "       COUNT(ef.EXAM_FILE_ID) as numberFile");
        sb.append(" from user_exam ue LEFT JOIN exam e ON ue.EXAM_ID = e.EXAM_ID " +
                "LEFT JOIN exam_file ef ON ue.USER_EXAM_ID = ef.OBJECT_ID WHERE ue.USER_EXAM_ID = :userExamId AND ef.TYPE = 2");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userExamId", userExamId);
        List<Object[]>result = query.getResultList();
        UserExamDto dto = new UserExamDto();
        if(!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                dto.setUserExamId(ValueUtil.getLongByObject(obj[0]));
                dto.setUsername(ValueUtil.getStringByObject(obj[1]));
                dto.setExamId(ValueUtil.getLongByObject(obj[2]));
                dto.setExamCode(ValueUtil.getStringByObject(obj[3]));
                dto.setSubmitTime(ValueUtil.getDateByObject(obj[4]));
                dto.setScore(ValueUtil.getDoubleByObject(obj[5]));
                dto.setNote(ValueUtil.getStringByObject(obj[6]));
                dto.setStatus(ValueUtil.getIntegerByObject(obj[7]));
                dto.setNumberFile(ValueUtil.getLongByObject(obj[8]));
            }
        }
        return dto;
    }

    public Query createQuery(StringBuilder sb, UserExamSearchDto searchDto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim().toUpperCase() + "%");
        }
        if (searchDto.getScore() != null) {
            query.setParameter("score", searchDto.getScore());
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        return query;

    }

    public void appendQuery(StringBuilder sb, UserExamSearchDto searchDto) {
        sb.append(" from user_exam ue LEFT JOIN exam e ON ue.EXAM_ID = e.EXAM_ID WHERE 1 = 1");
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            sb.append(" AND (lower(ue.USERNAME) LIKE :keyword " +
                    "OR lower(e.CODE) LIKE lower(:keyword)" +
                    "OR lower(ue.NOTE) LIKE lower(:keyword)) " );
        }
        if (searchDto.getScore() != null) {
            sb.append(" AND ue.SCORE =:score ");
        }
        if (searchDto.getStatus() != null) {
            sb.append(" AND ue.STATUS =:status ");
        }
    }
}
