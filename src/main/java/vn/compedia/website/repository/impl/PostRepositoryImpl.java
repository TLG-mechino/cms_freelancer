package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.PostDto;
import vn.compedia.website.dto.PostSearchDto;
import vn.compedia.website.repository.PostRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepositoryCustom {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<PostDto> getAllPostByUserName (String username, PostSearchDto dto) {
        StringBuilder sb = new StringBuilder();
        sb.append("select p.POST_ID, " +
                "       p.CONTENT, " +
                "       p.USERNAME, " +
                "       p.POSTING_TIME, " +
                "       p.BLOCK_COMMENT, " +
                "       p.STATUS, " +
                "       result1.postFile ");
        appendQuery(sb,dto, username);
        if (dto.getSortField() != null) {
            if (dto.getSortField().equals("username")) {
                sb.append(" ORDER BY p.USERNAME ");
            }
            if (dto.getSortField().equals("content")) {
                sb.append(" ORDER BY p.CONTENT ");
            }
            if (dto.getSortField().equals("postingTime")) {
                sb.append(" ORDER BY p.POSTING_TIME ");
            }
            if (dto.getSortField().equals("blockComment")) {
                sb.append(" ORDER BY p.BLOCK_COMMENT ");
            }
            if (dto.getSortField().equals("filePost")) {
                sb.append(" ORDER BY result1.postFile ");
            }
            if (dto.getSortField().equals("status")) {
                sb.append(" ORDER BY p.STATUS ");
            }
            sb.append(dto.getSortOrder());
        }
        else {
            sb.append(" ORDER BY p.POST_ID ");
        }
        Query query = createQuery(sb,dto,username);
        if (dto.getPageSize() > 0) {
            query.setFirstResult(dto.getPageIndex()*dto.getPageSize());
            query.setMaxResults(dto.getPageSize());
        }
        else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<Object[]> result = query.getResultList();
        List<PostDto>dtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(result)) {
           for (Object[] obj : result) {
               PostDto responseDto = new PostDto();
               responseDto.setId(ValueUtil.getLongByObject(obj[0]));
                responseDto.setContent(ValueUtil.getStringByObject(obj[1]));
                responseDto.setUsername(ValueUtil.getStringByObject(obj[2]));
                responseDto.setPostingTime(ValueUtil.getDateByObject(obj[3]));
                responseDto.setBlockComment(ValueUtil.getIntegerByObject(obj[4]));
                responseDto.setStatus(ValueUtil.getIntegerByObject(obj[5]));
                responseDto.setFilePost(null==ValueUtil.getStringByObject(obj[6])?null:ValueUtil.getStringByObject(obj[6]));
                dtos.add(responseDto);
           }
        }
        return dtos;
    }

    @Override
    public BigInteger countSearchRpByUserName(String username, PostSearchDto dto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select COUNT(p.POST_ID) ");
        appendQuery(sb,dto, username);
        Query query = createQuery(sb,dto,username);
        return (BigInteger) query.getSingleResult();
    }


    public Query createQuery (StringBuilder sb , PostSearchDto dto , String username) {
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("username", username);
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            query.setParameter("keyword","%"+dto.getKeyword().trim()+"%");
        }
        if (dto.getStatus()!=null) {
            query.setParameter("status",dto.getStatus());
        }
        return query;
    }

    public void appendQuery (StringBuilder sb , PostSearchDto dto, String username) {
        sb.append(" from post p" +
                "         left join (select pf.POST_ID, group_concat(pf.FILE_NAME) as postFile" +
                "                     from post_file pf" +
                "                     group by pf.POST_ID) result1 on p.POST_ID = result1.POST_ID" +
                "    where p.USERNAME = :username");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("username",username);
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            sb.append("     and ( (p.CONTENT like :keyword) or (p.USERNAME like :keyword) )");
        }
        if (dto.getStatus()!=null) {
            sb.append(" and p.STATUS = :status ");
        }
    }
}
