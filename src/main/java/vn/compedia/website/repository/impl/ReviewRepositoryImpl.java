package vn.compedia.website.repository.impl;

import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.response.ReviewResponseDto;
import vn.compedia.website.dto.search.ReviewSearchDto;
import vn.compedia.website.repository.ReviewRepositoryCustom;
import vn.compedia.website.util.DateUtil;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ReviewResponseDto> getAllReviewByUserName(String userName, ReviewSearchDto reviewSearchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rv.REVIEW_ID," +
                "       rv.USERNAME," +
                "       rv.CONTENT," +
                "       rv.STAR_AMOUNT," +
                "       rv.STATUS," +
                "       rv.REVIEW_TIME," +
                "       result1.NAME," +
                "       result1.JOB_ID,"+
                "       rv.TITLE ");
        appendQueryByUserName(sb,reviewSearchDto);
        if (reviewSearchDto.getSortField() != null ) {
            if (reviewSearchDto.getSortField().equals("content")) {
                sb.append(" ORDER BY rv.CONTENT ");
            }
            if (reviewSearchDto.getSortField().equals("startAmount")) {
                sb.append(" ORDER BY rv.STAR_AMOUNT");
            }
            if (reviewSearchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY rv.STATUS");
            }
            if (reviewSearchDto.getSortField().equals("reviewTime")) {
                sb.append(" ORDER BY rv.REVIEW_TIME");
            }
            if (reviewSearchDto.getSortField().equals("name")) {
                sb.append(" ORDER BY result1.NAME");
            }
            if (reviewSearchDto.getSortField().equals("title")) {
                sb.append(" ORDER BY rv.TITLE");
            }
            sb.append(reviewSearchDto.getSortOrder());
        }
        else {
            sb.append(" ORDER BY rv.REVIEW_ID DESC");
        }
        Query query = createQueryByUserName(userName,sb,reviewSearchDto);
        if (reviewSearchDto.getPageSize() > 0) {
            query.setFirstResult(reviewSearchDto.getPageSize() * reviewSearchDto.getPageIndex());
            query.setMaxResults(reviewSearchDto.getPageIndex());
        }
        else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<ReviewResponseDto>reviews = new ArrayList<>();
        List<Object[]>result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj:result) {
                ReviewResponseDto review = new ReviewResponseDto();
                review.setId(ValueUtil.getLongByObject(obj[0]));
                review.setUserName(ValueUtil.getStringByObject(obj[1]));
                review.setContent(null == ValueUtil.getStringByObject(obj[2])?"":ValueUtil.getStringByObject(obj[2]));
                review.setStarAmount(null == ValueUtil.getIntegerByObject(obj[3])?0:ValueUtil.getIntegerByObject(obj[3]));
                review.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                review.setReviewTime(DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[5]),DateUtil.DATE_FORMAT));
                review.setNameJob(ValueUtil.getStringByObject(obj[6]));
                review.setJobId(null == ValueUtil.getLongByObject(obj[7])?null:ValueUtil.getLongByObject(obj[7]));
                review.setTitle(ValueUtil.getStringByObject(obj[8]));
                reviews.add(review);
            }
        }
        return reviews;
    }

    private void appendQueryByUserName (StringBuilder sb , ReviewSearchDto dto) {
        sb.append(" from review rv " +
                "  inner join (select j.JOB_ID, j.NAME from job j where j.USERNAME = :userName) result1" +
                "   on rv.JOB_ID = result1.JOB_ID " +
                " where 1 = 1 ");
        if (dto.getKeyword() != null) {
            sb.append(" and ( (rv.TITLE like :keyword) or (rv.START_AMOUNT like :keyword) )");
        }
        if (dto.getStatus() != null) {
            sb.append(" and rv.STATUS =:status");
        }
    }

    private Query createQueryByUserName (String userName, StringBuilder sb,ReviewSearchDto dto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName",userName);
        if (dto.getKeyword() !=null ) {
            query.setParameter("keyword",dto.getKeyword());
        }
        if (dto.getStatus() != null ) {
            query.setParameter("status",dto.getStatus());
        }
        return query;
    }


    @Override
    public int countSearchByUserName(String userName, ReviewSearchDto dto) {
        return 0;
    }

}
