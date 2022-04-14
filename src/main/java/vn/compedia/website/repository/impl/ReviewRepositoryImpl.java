package vn.compedia.website.repository.impl;

import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.ReviewDto;
import vn.compedia.website.dto.response.ReviewResponseDto;
import vn.compedia.website.dto.ReviewSearchDto;
import vn.compedia.website.repository.ReviewRepositoryCustom;
import vn.compedia.website.util.DateUtil;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ReviewDto> getAllReviewByUserName(String username, ReviewSearchDto reviewSearchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rv.REVIEW_ID," +
                "       rv.USERNAME," +
                "       rv.CONTENT," +
                "       rv.STAR_AMOUNT," +
                "       rv.STATUS," +
                "       rv.REVIEW_TIME," +
                "       result1.NAME_VN," +
                "       result1.JOB_ID,"+
                "       rv.TITLE ");
        appendQueryByUserName(sb,reviewSearchDto, username);
        if (reviewSearchDto.getSortField() != null ) {

            if(reviewSearchDto.getSortField().equals("username")){
                sb.append(" ORDER BY rv.USERNAME ");
            }
            if (reviewSearchDto.getSortField().equals("content")) {
                sb.append(" ORDER BY rv.CONTENT ");
            }
            if (reviewSearchDto.getSortField().equals("starAmount")) {
                sb.append(" ORDER BY rv.STAR_AMOUNT ");
            }
            if (reviewSearchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY rv.STATUS ");
            }
            if (reviewSearchDto.getSortField().equals("reviewTime")) {
                sb.append(" ORDER BY rv.REVIEW_TIME ");
            }
            if (reviewSearchDto.getSortField().equals("nameJob")) {
                sb.append(" ORDER BY result1.NAME_VN ");
            }
            if (reviewSearchDto.getSortField().equals("title")) {
                sb.append(" ORDER BY rv.TITLE ");
            }
            sb.append(reviewSearchDto.getSortOrder());
        }
        else {
            sb.append(" ORDER BY rv.REVIEW_ID DESC ");
        }
        Query query = createQueryByUserName(username,sb,reviewSearchDto);
        if (reviewSearchDto.getPageSize() > 0) {
            query.setFirstResult(reviewSearchDto.getPageSize() * reviewSearchDto.getPageIndex());
            query.setMaxResults(reviewSearchDto.getPageIndex());
        }
        else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<ReviewDto>reviews = new ArrayList<>();
        List<Object[]>result = query.getResultList();
        if (!CollectionUtils.isEmpty(result)) {
            for (Object[] obj:result) {
                ReviewDto review = new ReviewDto();
                review.setId(ValueUtil.getLongByObject(obj[0]));
                review.setUsername(ValueUtil.getStringByObject(obj[1]));
                review.setContent(null == ValueUtil.getStringByObject(obj[2])?"":ValueUtil.getStringByObject(obj[2]));
                review.setStarAmount(null == ValueUtil.getIntegerByObject(obj[3])?0:ValueUtil.getIntegerByObject(obj[3]));
                review.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                review.setReviewTime(ValueUtil.getDateByObject(obj[5]));
                review.setNameJob(ValueUtil.getStringByObject(obj[6]));
                review.setJobId(null == ValueUtil.getLongByObject(obj[7])?null:ValueUtil.getLongByObject(obj[7]));
                review.setTitle(ValueUtil.getStringByObject(obj[8]));
                reviews.add(review);
            }
        }
        return reviews;
    }

    @Override
    public BigInteger countSearchByUserName(String username, ReviewSearchDto dto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select COUNT(rv.REVIEW_ID) ");
        appendQueryByUserName(sb,dto, username);
        Query query = createQueryByUserName(username,sb,dto);
        return (BigInteger) query.getSingleResult();
    }

    private void appendQueryByUserName (StringBuilder sb , ReviewSearchDto dto, String username) {
        sb.append(" from review rv " +
                "  inner join (select j.JOB_ID, j.NAME from job j where j.USERNAME = :username) result1" +
                "   on rv.JOB_ID = result1.JOB_ID " +
                " where 1 = 1 ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("username",username);
        if (dto.getKeyword() != null) {
            sb.append(" and ( (lower(rv.TITLE) like lower(:keyword)) or (lower(result1.NAME_VN) like lower(:keyword)) ) ");
        }
        if(dto.getStartAmount() != null){
            sb.append(" and rv.START_AMOUNT =:startAmount ");
        }
        if (dto.getStatus() != null) {
            sb.append(" and rv.STATUS =:status ");
        }
    }

    private Query createQueryByUserName (String username, StringBuilder sb,ReviewSearchDto dto) {
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("username",username);
        if (dto.getKeyword() !=null ) {
            query.setParameter("keyword",dto.getKeyword());
        }
        if(dto.getStartAmount() != null){
            query.setParameter("startAmount",dto.getStartAmount());
        }
        if (dto.getStatus() != null ) {
            query.setParameter("status",dto.getStatus());
        }
        return query;
    }




}
