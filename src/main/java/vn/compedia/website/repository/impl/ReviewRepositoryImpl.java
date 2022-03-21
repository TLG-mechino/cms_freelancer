package vn.compedia.website.repository.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.response.ReviewResponseDto;
import vn.compedia.website.repository.ReviewRepositoryCustom;
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
    public List<ReviewResponseDto> getAllReviewByUserName(String userName, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select rv.REVIEW_ID," +
                "       rv.USERNAME," +
                "       rv.CONTENT," +
                "       rv.STAR_AMOUNT," +
                "       rv.STATUS," +
                "       rv.REVIEW_TIME," +
                "       result1.NAME," +
                "       result1.JOB_ID" +
                " from review rv" +
                "         inner join (select j.JOB_ID, j.NAME from job j where j.USERNAME = :userName) result1" +
                "                    on rv.JOB_ID = result1.JOB_ID");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName",userName);
        int positionStart = pageable.getPageNumber() * pageable.getPageSize();
        query.setFirstResult(positionStart);
        query.setMaxResults(pageable.getPageSize());
        List<ReviewResponseDto>reviews = new ArrayList<>();
        List<Object[]>result = query.getResultList();
        if(!CollectionUtils.isEmpty(result)){
            for(Object[] obj:result){
                ReviewResponseDto review = new ReviewResponseDto();
                review.setId(null == ValueUtil.getLongByObject(obj[0])?null:ValueUtil.getLongByObject(obj[0]));
                review.setUserName(null == ValueUtil.getStringByObject(obj[1])?"":ValueUtil.getStringByObject(obj[1]));
                review.setContent(null == ValueUtil.getStringByObject(obj[2])?"":ValueUtil.getStringByObject(obj[2]));
                review.setStarAmount(null == ValueUtil.getIntegerByObject(obj[3])?0:ValueUtil.getIntegerByObject(obj[3]));
                review.setStatus(null == ValueUtil.getIntegerByObject(obj[4])?0:ValueUtil.getIntegerByObject(obj[4]));
                review.setReviewTime(null == ValueUtil.getStringByObject(obj[5])?"":ValueUtil.getStringByObject(obj[5]));
                review.setNameJob(null == ValueUtil.getStringByObject(obj[6])?"":ValueUtil.getStringByObject(obj[6]));
                review.setJobId(null == ValueUtil.getLongByObject(obj[7])?null:ValueUtil.getLongByObject(obj[7]));
                reviews.add(review);
            }
        }
        return reviews;
    }

}
