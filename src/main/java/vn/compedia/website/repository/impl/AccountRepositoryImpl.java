package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.dto.AccountSearchDto;
import vn.compedia.website.repository.AccountRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean checkExistEmail(String email) {
        Query query = entityManager.createQuery("SELECT count(accountId) FROM Account WHERE email = :email ");
        query.setParameter("email", email);
        return (long) query.getSingleResult() > 0;
    }

    @Override
    public List<AccountDto> search(AccountSearchDto searchDto, Integer type) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT a.ACCOUNT_ID, " +
                "       a.USERNAME, " +
                "       a.EMAIL, " +
                "       a.PASSWORD, " +
                "       a.SALT, " +
                "       a.STATUS, " +
                "       a.CREATE_DATE, " +
                "       a.CREATE_BY, " +
                "       a.UPDATE_DATE, " +
                "       a.UPDATE_BY, " +
                "       a.FULL_NAME,  " +
                "       a.PHONE");
        appendQuery(sb, searchDto, type);

        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("username")) {
                sb.append(" ORDER BY a.USERNAME ");
            }
            if (searchDto.getSortField().equals("email")) {
                sb.append(" ORDER BY a.EMAIL ");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY a.STATUS ");
            }
            if (searchDto.getSortField().equals("phone")) {
                sb.append(" ORDER BY a.PHONE ");
            }
            if (searchDto.getSortField().equals("fullName")) {
                sb.append(" ORDER BY a.FULL_NAME ");
            }
            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY a.ACCOUNT_ID DESC ");
        }

        Query query = createQuery(sb, searchDto, type);
        if (searchDto.getPageSize() > 0) {
            query.setFirstResult(searchDto.getPageIndex());
            query.setMaxResults(searchDto.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<Object[]> resultList = query.getResultList();
        List<AccountDto> list = new ArrayList<>();
        for (Object[] obj : resultList) {
            AccountDto accountDto = new AccountDto();
            accountDto.setAccountId(ValueUtil.getLongByObject(obj[0]));
            accountDto.setUsername(ValueUtil.getStringByObject(obj[1]));
            accountDto.setEmail(ValueUtil.getStringByObject(obj[2]));
            accountDto.setPassword(ValueUtil.getStringByObject(obj[3]));
            accountDto.setSalt(ValueUtil.getStringByObject(obj[4]));
            accountDto.setStatus(ValueUtil.getIntegerByObject(obj[5]));
            accountDto.setCreateDate(ValueUtil.getDateByObject(obj[6]));
            accountDto.setCreateBy(ValueUtil.getStringByObject(obj[7]));
            accountDto.setUpdateDate(ValueUtil.getDateByObject(obj[8]));
            accountDto.setUpdateBy(ValueUtil.getStringByObject(obj[9]));
            accountDto.setFullName(ValueUtil.getStringByObject(obj[10]));
            accountDto.setPhone(ValueUtil.getStringByObject(obj[11]));
            list.add(accountDto);
        }
        return list;
    }

    @Override
    public BigInteger countSearch(AccountSearchDto searchDto, Integer type) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(a.ACCOUNT_ID) ");
        appendQuery(sb, searchDto, type);
        Query query = createQuery(sb, searchDto, type);
        return (BigInteger) query.getSingleResult();
    }

    public Query createQuery(StringBuilder sb, AccountSearchDto searchDto, Integer type) {
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("accountId", searchDto.getAccountId());
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim().toLowerCase() + "%");
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        if (null != type) {
            query.setParameter("type", type);
        }
        return query;
    }

    public void appendQuery(StringBuilder sb, AccountSearchDto searchDto, Integer type) {
        sb.append(" FROM account a where a.ACCOUNT_ID <> :accountId ");
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            sb.append(" AND (lower(a.USERNAME) LIKE :keyword OR lower(a.FULL_NAME) LIKE :keyword or lower(a.PHONE) LIKE :keyword OR lower(a.EMAIL) LIKE :keyword) ");
        }
        if (searchDto.getStatus() != null) {
            sb.append(" AND a.STATUS =:status ");
        }
        if (null != type) {
            sb.append(" AND a.TYPE = :type");
        }
    }
}
