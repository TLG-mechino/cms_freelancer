package vn.compedia.website.repository.impl;

import com.ocpsoft.pretty.faces.util.StringUtils;
import vn.compedia.website.dto.entity.UserDto;
import vn.compedia.website.dto.search.UserSearchDto;
import vn.compedia.website.repository.UserRepositoryCustom;
import vn.compedia.website.util.StringUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<UserDto> search(UserSearchDto userSearchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("select user.USERNAME," +
                "       user.IMAGE_PATH," +
                "       user.ADDRESS," +
                "       user.TOTAL_SCORE," +
                "       user.MONEY_WALLET," +
                "       user.PROVINCE_ID," +
                "       user.DISTRICT_ID," +
                "       user.COMMUNE_ID," +
                "       user.EXPERIENCE_AMOUNT," +
                "       user.RENT_COST," +
                "       user.WORKING_HOURS," +
                "       user.TIME_TYPE_ID," +
                "       user.FACEBOOK_LINK," +
                "       user.TYPE_LOGIN");
        appendQuery(sb,userSearchDto);
        if (userSearchDto.getSortField() != null) {
           if(userSearchDto.getSortField().equals("userName")) {
               sb.append(" ORDER BY user.USERNAME");
           }
           if (userSearchDto.getSortField().equals("imagePath")) {
               sb.append(" ORDER BY user.IMAGE_PATH");
           }
           if (userSearchDto.getSortField().equals("address")) {
               sb.append(" ORDER BY user.ADDRESS");
           }
           if (userSearchDto.getSortField().equals("totalScore")) {
               sb.append(" ORDER BY user.TOTAL_SCORE");
           }
           if (userSearchDto.getSortField().equals("moneyWallet")) {
               sb.append(" ORDER BY user.MONEY_WALLET");
           }
           if (userSearchDto.getSortField().equals("provinceId")) {
               sb.append(" ORDER BY user.PROVINCE_ID");
           }
           if (userSearchDto.getSortField().equals("districtId")) {
               sb.append(" ORDER BY user.DISTRICT_ID");
           }
           if (userSearchDto.getSortField().equals("communeId")) {
               sb.append(" ORDER BY user.COMMUNE_ID");
           }
           if (userSearchDto.getSortField().equals("experienceAmount")) {
               sb.append(" ORDER BY user.EXPERIENCE_AMOUNT");
           }
           if (userSearchDto.getSortField().equals("rentCost")) {
               sb.append(" ORDER BY user.RENT_COST");
           }
           if (userSearchDto.getSortField().equals("workingHours")) {
               sb.append(" ORDER BY user.WORKING_HOURS");
           }
           if (userSearchDto.getSortField().equals("timeTypeId")) {
               sb.append(" ORDER BY user.TIME_TYPE_ID");
           }
           if (userSearchDto.getSortField().equals("faceBookLink")) {
               sb.append(" ORDER BY user.FACEBOOK_LINK");
           }
           if(userSearchDto.getSortField().equals("typeLogin")){
               sb.append(" ORDER BY user.TYPE_LOGIN");
           }
           sb.append(userSearchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY user.USERNAME DESC");
        }
        Query query = createQuery(sb,userSearchDto);
        if(userSearchDto.getPageSize() > 0) {
            query.setFirstResult(userSearchDto.getPageIndex());
            query.setMaxResults(userSearchDto.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<Object[]> resultList = query.getResultList();
        List<UserDto> userDtos = new ArrayList<>();
        for(Object[] obj : resultList){

        }


        return null;
    }

    public Query createQuery(StringBuilder sb , UserSearchDto dto){
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("keyword","%" + dto.getKeyword().trim() + "%");
        return query;
    }

    public void appendQuery(StringBuilder sb,UserSearchDto dto) {
        sb.append(" from user user where 1 = 1 ");
        if(StringUtils.isNotBlank(dto.getKeyword())){
            sb.append(" AND (lower(user.USERNAME) LIKE lower(:keyword)) "+
                    " OR lower(user.ADDRESS) LIKE lower(:keyword))");
        }
    }
}
