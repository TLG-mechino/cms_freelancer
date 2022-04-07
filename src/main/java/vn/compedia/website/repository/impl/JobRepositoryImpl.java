package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;

import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.JobDto;
import vn.compedia.website.dto.JobSearchDto;
import vn.compedia.website.repository.JobRepositoryCustom;
import vn.compedia.website.util.ValueUtil;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class JobRepositoryImpl implements JobRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<JobDto> getAllJobRpByUserName(String username, JobSearchDto jobSearchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select j.JOB_ID, " +
                "       j.NAME, " +
                "       j.DESCRIPTION, " +
                "       j.USERNAME, " +
                "       j.MONEY_FROM, " +
                "       j.MONEY_TO, " +
                "       j.CREATE_DATE, " +
                "       j.UPDATE_DATE, " +
                "       j.STATUS ");
        appendQueryByUserName(sb, jobSearchDto, username);


        if (jobSearchDto.getSortField() != null) {
            if (jobSearchDto.getSortField().equals("name")) {
                sb.append(" ORDER BY j.NAME ");
            }
            if (jobSearchDto.getSortField().equals("description")) {
                sb.append(" ORDER BY j.DESCRIPTION ");
            }
            if (jobSearchDto.getSortField().equals("username")) {
                sb.append(" ORDER BY j.USERNAME ");
            }
            if (jobSearchDto.getSortField().equals("moneyFrom")) {
                sb.append(" ORDER BY j.MONEY_FROM ");
            }
            if (jobSearchDto.getSortField().equals("moneyTo")) {
                sb.append(" ORDER BY j.MONEY_TO ");
            }
            if (jobSearchDto.getSortField().equals("createDate")) {
                sb.append(" ORDER BY j.CREATE_DATE ");
            }
            if (jobSearchDto.getSortField().equals("updateDate")) {
                sb.append(" ORDER BY j.UPDATE_DATE ");
            }
            if (jobSearchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY j.STATUS ");
            }
            sb.append(jobSearchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY j.JOB_ID DESC");
        }
        Query query = createQueryByUserName(sb, jobSearchDto, username);

        if (jobSearchDto.getPageSize() > 0) {
            query.setFirstResult(jobSearchDto.getPageIndex());
            query.setMaxResults(jobSearchDto.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<Object[]> resultList = query.getResultList();
        List<JobDto> jobs = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resultList)) {
            for (Object[] obj : resultList) {
                JobDto job = new JobDto();
                job.setId(ValueUtil.getLongByObject(obj[0]));
                job.setName(ValueUtil.getStringByObject(obj[1]));
                job.setDescription(ValueUtil.getStringByObject(obj[2]));
                job.setUsername(ValueUtil.getStringByObject(obj[3]));
                job.setMoneyFrom(ValueUtil.getDoubleByObject(obj[4]));
                job.setMoneyTo(ValueUtil.getDoubleByObject(obj[5]));
                job.setCreateTime(ValueUtil.getDateByObject(obj[6]));
                job.setModifiedTime(ValueUtil.getDateByObject(obj[7]));
                job.setStatus(ValueUtil.getIntegerByObject(obj[8]));
                jobs.add(job);
            }
        }
        return jobs;
    }

    @Override
    public BigInteger countSearchRpByUserName(String username, JobSearchDto jobSearchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(j.JOB_ID) ");
        appendQueryByUserName(sb,jobSearchDto, username);
        Query query = createQueryByUserName(sb,jobSearchDto,username);
        return (BigInteger) query.getSingleResult();
    }

    public void appendQueryByUserName(StringBuilder sb, JobSearchDto dto, String username) {
        sb.append(" FROM job j WHERE j.USERNAME = :username ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("username", username);
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            sb.append(" AND lower(j.NAME) LIKE lower(:keyword)) " );
        }
        if (dto.getMoney() != null) {
            sb.append(" AND j.MONEY_FROM <= :money AND j.MONEY_TO >= :money ");
        }
        if (dto.getStatus() != null) {
            sb.append(" AND j.STATUS =:status ");
        }
    }


    public Query createQueryByUserName(StringBuilder sb, JobSearchDto dto, String username) {
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("username", username);
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            query.setParameter("keyword", "%" + dto.getKeyword().trim() + "%");
        }
        if (dto.getMoney() != null) {
            query.setParameter("money", dto.getMoney());
        }
        if (dto.getStatus() != null) {
            query.setParameter("status", dto.getStatus());
        }
        return query;
    }
}
