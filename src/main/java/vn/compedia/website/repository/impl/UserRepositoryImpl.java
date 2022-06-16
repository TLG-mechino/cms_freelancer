package vn.compedia.website.repository.impl;

import com.ocpsoft.pretty.faces.util.StringUtils;
import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.UserExamDto;
import vn.compedia.website.dto.entity.UserDto;
import vn.compedia.website.dto.response.TotalJobByDateResponse;
import vn.compedia.website.dto.response.TotalUserByDateResponse;
import vn.compedia.website.dto.search.UserSearchDto;
import vn.compedia.website.mapper.EntityMapper;
import vn.compedia.website.repository.UserRepositoryCustom;
import vn.compedia.website.util.DateUtil;
import vn.compedia.website.util.DbConstant;
import vn.compedia.website.util.StringUtil;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<UserDto> search(UserSearchDto userSearchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ac.ACCOUNT_ID," +
                "       ac.FULL_NAME," +
                "       ac.PHONE," +
                "       ac.EMAIL," +
                "       ac.CREATE_DATE," +
                "       ac.STATUS," +
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
                "       user.TYPE_LOGIN," +
                "       user.IS_EDITOR," +
                "       user.IS_USER," +
                "       user.DATE_OF_BIRTH," +
                "       user.DESCRIPTION_USER ");
        appendQuery(sb,userSearchDto);

        if (userSearchDto.getSortField() != null) {
            if (userSearchDto.getSortField().equals("fullName")) {
                sb.append(" ORDER BY ac.FULL_NAME ");
            }
            if (userSearchDto.getSortField().equals("phone")) {
                sb.append(" ORDER BY ac.PHONE ");
            }
            if (userSearchDto.getSortField().equals("email")) {
                sb.append(" ORDER BY ac.EMAIL ");
            }
            if (userSearchDto.getSortField().equals("imagePath")) {
                sb.append(" ORDER BY user.IMAGE_PATH ");
            }
            if (userSearchDto.getSortField().equals("address")) {
                sb.append(" ORDER BY user.ADDRESS ");
            }
            if (userSearchDto.getSortField().equals("totalScore")) {
                sb.append(" ORDER BY user.TOTAL_SCORE ");
            }
            if (userSearchDto.getSortField().equals("moneyWallet")) {
                sb.append(" ORDER BY user.MONEY_WALLET ");
            }
            if (userSearchDto.getSortField().equals("experienceAmount")) {
                sb.append(" ORDER BY user.EXPERIENCE_AMOUNT ");
            }
            if (userSearchDto.getSortField().equals("rentCost")) {
                sb.append(" ORDER BY user.RENT_COST ");
            }
            if (userSearchDto.getSortField().equals("workingHours")) {
                sb.append(" ORDER BY user.WORKING_HOURS ");
            }
            if (userSearchDto.getSortField().equals("facebookLink")) {
                sb.append(" ORDER BY user.FACEBOOK_LINK ");
            }
            if (userSearchDto.getSortField().equals("typeLogin")) {
                sb.append(" ORDER BY user.TYPE_LOGIN ");
            }
            if (userSearchDto.getSortField().equals("isEditor")) {
                sb.append(" ORDER BY user.IS_EDITOR ");
            }
            if (userSearchDto.getSortField().equals("isUser")) {
                sb.append(" ORDER BY user.IS_USER ");
            }
            if (userSearchDto.getSortField().equals("dateOfBirth")) {
                sb.append(" ORDER BY user.DATE_OF_BIRTH ");
            }
            if (userSearchDto.getSortField().equals("descriptionUser")) {
                sb.append(" ORDER BY user.DESCRIPTION_USER ");
            }
            sb.append(userSearchDto.getSortOrder());
        }
        else {
            sb.append(" ORDER BY ac.ACCOUNT_ID DESC ");
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
        for (Object[] obj : resultList) {
            UserDto userDto = new UserDto();
            userDto.setAccountId(null == ValueUtil.getLongByObject(obj[0]) ? null : ValueUtil.getLongByObject(obj[0]));
            userDto.setFullName(null == ValueUtil.getStringByObject(obj[1]) ? null : ValueUtil.getStringByObject(obj[1]));
            userDto.setPhone(null == ValueUtil.getStringByObject(obj[2]) ? null : ValueUtil.getStringByObject(obj[2]));
            userDto.setEmail(null == ValueUtil.getStringByObject(obj[3]) ? null : ValueUtil.getStringByObject(obj[3]));
            userDto.setCreateDate(null == ValueUtil.getDateByObject(obj[4]) ? null :
                    DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[4]), "HH:mm, dd/MM/yyyy"));
            userDto.setStatus(null == ValueUtil.getIntegerByObject(obj[5]) ? null : ValueUtil.getIntegerByObject(obj[5]));
            userDto.setImagePath(null == ValueUtil.getStringByObject(obj[6]) ? null : ValueUtil.getStringByObject(obj[6]));
            userDto.setAddress(null == ValueUtil.getStringByObject(obj[7]) ? null : ValueUtil.getStringByObject(obj[7]));
            userDto.setTotalScore(null == ValueUtil.getIntegerByObject(obj[8]) ? null : ValueUtil.getIntegerByObject(obj[8]));
            userDto.setMoneyWallet(null == ValueUtil.getDoubleByObject(obj[9]) ? null : ValueUtil.getDoubleByObject(obj[9]));
            userDto.setProvinceId(null == ValueUtil.getLongByObject(obj[10]) ? null : ValueUtil.getLongByObject(obj[10]));
            userDto.setDistrictId(null == ValueUtil.getLongByObject(obj[11]) ? null : ValueUtil.getLongByObject(obj[11]));
            userDto.setCommuneId(null == ValueUtil.getLongByObject(obj[12]) ? null : ValueUtil.getLongByObject(obj[12]));
            userDto.setExperienceAmount(null == ValueUtil.getIntegerByObject(obj[13]) ? null : ValueUtil.getIntegerByObject(obj[13]));
            userDto.setRentCost(null == ValueUtil.getDoubleByObject(obj[14]) ? null : ValueUtil.getDoubleByObject(obj[14]));
            userDto.setWorkingHours(null == ValueUtil.getIntegerByObject(obj[15]) ? null : ValueUtil.getIntegerByObject(obj[15]));
            userDto.setTimeTypeId(null == ValueUtil.getIntegerByObject(obj[16]) ? null : ValueUtil.getIntegerByObject(obj[16]));
            userDto.setFacebookLink(null == ValueUtil.getStringByObject(obj[17]) ? null : ValueUtil.getStringByObject(obj[17]));
            userDto.setTypeLogin(null == ValueUtil.getStringByObject(obj[18]) ? null : ValueUtil.getStringByObject(obj[18]));
            userDto.setIsEditor(null == ValueUtil.getIntegerByObject(obj[19]) ? null : ValueUtil.getIntegerByObject(obj[19]));
            userDto.setIsUser(null == ValueUtil.getIntegerByObject(obj[20]) ? null : ValueUtil.getIntegerByObject(obj[20]));
            userDto.setDateOfBirth(null == ValueUtil.getDateByObject(obj[21]) ? null : ValueUtil.getDateByObject(obj[21]));
            userDto.setDescriptionUser(null == ValueUtil.getStringByObject(obj[22]) ? null : ValueUtil.getStringByObject(obj[22]));
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public int countSearch(UserSearchDto userSearchDto, int type) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(ac.ACCOUNT_ID) ");
        appendQuery(sb,userSearchDto);
        Query query = createQuery(sb,userSearchDto);
        return ValueUtil.getIntegerByObject(query.getSingleResult());
    }

    @Override
    public UserDto findUserDtoById(Long accountId) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select ac.ACCOUNT_ID, " +
                "       ac.FULL_NAME, " +
                "       ac.PHONE, " +
                "       ac.EMAIL, " +
                "       user.FACEBOOK_LINK, " +
                "       user.ADDRESS," +
                "       user.TOTAL_SCORE, " +
                "       user.MONEY_WALLET, " +
                "       user.PROVINCE_ID, " +
                "       user.DISTRICT_ID, " +
                "       user.COMMUNE_ID, " +
                "       user.EXPERIENCE_AMOUNT," +
                "       user.WORKING_HOURS, " +
                "       user.DESCRIPTION_USER, " +
                "       language.NAME, " +
                "       user.USERNAME, " +
                "       user.IMAGE_PATH, " +
                "       user.IS_EDITOR, " +
                "       user.IS_USER, " +
                "       user.RENT_COST, " +
                "       user.TIME_TYPE_ID, " +
                "       user.TYPE_LOGIN, " +
                "       user.LANGUAGE_ID ");
        sb.append("  from account ac inner join user user on ac.USERNAME = user.USERNAME " +
                "LEFT JOIN language language ON user.LANGUAGE_ID = language.LANGUAGE_ID WHERE ac.ACCOUNT_ID = :accountId ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("accountId", accountId);
        List<Object[]>result = query.getResultList();
        UserDto dto = new UserDto();
        if(!CollectionUtils.isEmpty(result)) {
            for (Object[] obj : result) {
                dto.setAccountId(ValueUtil.getLongByObject(obj[0]));
                dto.setFullName(ValueUtil.getStringByObject(obj[1]));
                dto.setPhone(ValueUtil.getStringByObject(obj[2]));
                dto.setEmail(ValueUtil.getStringByObject(obj[3]));
                dto.setFacebookLink(ValueUtil.getStringByObject(obj[4]));
                dto.setAddress(ValueUtil.getStringByObject(obj[5]));
                dto.setTotalScore(ValueUtil.getIntegerByObject(obj[6]));
                dto.setMoneyWallet(ValueUtil.getDoubleByObject(obj[7]));
                dto.setProvinceId(ValueUtil.getLongByObject(obj[8]));
                dto.setDistrictId(ValueUtil.getLongByObject(obj[9]));
                dto.setCommuneId(ValueUtil.getLongByObject(obj[10]));
                dto.setExperienceAmount(ValueUtil.getIntegerByObject(obj[11]));
                dto.setWorkingHours(ValueUtil.getIntegerByObject(obj[12]));
                dto.setDescriptionUser(ValueUtil.getStringByObject(obj[13]));
                dto.setLanguageName(ValueUtil.getStringByObject(obj[14]));
                dto.setId(ValueUtil.getStringByObject(obj[15]));
                dto.setImagePath(ValueUtil.getStringByObject(obj[16]));
                dto.setIsEditor(ValueUtil.getIntegerByObject(obj[17]));
                dto.setIsUser(ValueUtil.getIntegerByObject(obj[18]));
                dto.setRentCost(ValueUtil.getDoubleByObject(obj[19]));
                dto.setTimeTypeId(ValueUtil.getIntegerByObject(obj[20]));
                dto.setTypeLogin(ValueUtil.getStringByObject(obj[21]));
                dto.setLanguageId(ValueUtil.getIntegerByObject(obj[22]));
            }
        }
        return dto;
    }

    @Override
    public List<TotalUserByDateResponse> countUserByDate(Integer month, Integer year) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select count(u.USERNAME) as total, date_format(a.CREATE_DATE, '%d') as date " +
                " from user u inner join account a on u.USERNAME = a.USERNAME " +
                " where date_format(a.CREATE_DATE, '%m') = :month " +
                "  and date_format(a.CREATE_DATE, '%Y') = :year " +
                " group by date_format(a.CREATE_DATE, '%d') ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("month", month);
        query.setParameter("year", year);
        List result = EntityMapper.mapper(query, sb.toString(), TotalUserByDateResponse.class);
        return result;
    }

    public Query createQuery(StringBuilder sb , UserSearchDto dto){
        Query query = entityManager.createNativeQuery(sb.toString());
        if(StringUtils.isNotBlank(dto.getKeyword())) {
            query.setParameter("keyword", "%" + dto.getKeyword().trim() + "%");
        }
        query.setParameter("type", DbConstant.ACCOUNT_USER);
        if(dto.getStatus() != null) {
            query.setParameter("status",dto.getStatus());
        }
        return query;
    }

    public void appendQuery(StringBuilder sb,UserSearchDto dto) {
        sb.append(" from account ac inner join user user on ac.USERNAME = user.USERNAME where ac.TYPE = :type");
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            sb.append(" and ( (ac.FULL_NAME like :keyword) or (ac.PHONE like :keyword) or (ac.EMAIL like :keyword))");
        }
        if (dto.getStatus() != null) {
            sb.append("   and ac.STATUS = :status");
        }
    }
}
