package vn.compedia.website.repository.impl;

import com.ocpsoft.pretty.faces.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.response.JobResponseDto;
import vn.compedia.website.dto.search.JobUserSearchDto;
import vn.compedia.website.repository.JobRepositoryCustom;
import vn.compedia.website.util.DateUtil;
import vn.compedia.website.util.ValueUtil;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class JobRepositoryIml implements JobRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

//    @Override
    public List<JobResponseDto> getAllJobRpByUserName(String userName, JobUserSearchDto jobUserSearchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select j.JOB_ID," +
                "       j.CODE," +
                "       j.NAME," +
                "       j.DESCRIPTION," +
                "       j.USERNAME," +
                "       j.START_TIME," +
                "       j.END_TIME," +
                "       j.MONEY_FROM," +
                "       j.MONEY_TO," +
                "       j.CREATE_DATE," +
                "       j.UPDATE_DATE," +
                "       j.STATUS ");
        appendQueryByUserName(sb, jobUserSearchDto);


        if (jobUserSearchDto.getSortField() != null) {
            if (jobUserSearchDto.getSortField().equals("code")) {
                sb.append(" ORDER BY j.CODE");
            }
            if (jobUserSearchDto.getSortField().equals("name")) {
                sb.append(" ORDER BY j.NAME");
            }
            if (jobUserSearchDto.getSortField().equals("description")) {
                sb.append(" ORDER BY j.DESCRIPTION");
            }
            if (jobUserSearchDto.getSortField().equals("userName")) {
                sb.append(" ORDER BY j.USERNAME");
            }
            if (jobUserSearchDto.getSortField().equals("startTime")) {
                sb.append(" ORDER BY j.START_TIME");
            }
            if (jobUserSearchDto.getSortField().equals("endTime")) {
                sb.append(" ORDER BY j.END_TIME");
            }
            if (jobUserSearchDto.getSortField().equals("moneyFrom")) {
                sb.append(" ORDER BY j.MONEY_FROM");
            }
            if (jobUserSearchDto.getSortField().equals("moneyTo")) {
                sb.append(" ORDER BY j.MONEY_TO");
            }
            if (jobUserSearchDto.getSortField().equals("createDate")) {
                sb.append(" ORDER BY j.CREATE_DATE");
            }
            if (jobUserSearchDto.getSortField().equals("updateDate")) {
                sb.append(" ORDER BY j.UPDATE_DATE");
            }
            sb.append(jobUserSearchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY j.JOB_ID DESC");
        }

        Query query = createQueryByUserName(sb, jobUserSearchDto, userName);


        if (jobUserSearchDto.getPageSize() > 0) {
            query.setFirstResult(jobUserSearchDto.getPageIndex() * jobUserSearchDto.getPageSize());
            query.setMaxResults(jobUserSearchDto.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<Object[]> resultList = query.getResultList();
        List<JobResponseDto> jobs = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resultList)) {
            for (Object[] obj : resultList) {
                JobResponseDto job = new JobResponseDto();
                job.setId(ValueUtil.getLongByObject(obj[0]));
                job.setCodeJob(ValueUtil.getStringByObject(obj[1]));
                job.setName(ValueUtil.getStringByObject(obj[2]));
                job.setDescription(ValueUtil.getStringByObject(obj[3]));
                job.setUserName(ValueUtil.getStringByObject(obj[4]));
                job.setStartTime(DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[5]), DateUtil.DATE_FORMAT));
                job.setEndTime(DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[6]), DateUtil.DATE_FORMAT));
                job.setMoneyFrom(ValueUtil.getDoubleByObject(obj[7]));
                job.setMoneyTo(ValueUtil.getDoubleByObject(obj[8]));
                job.setCreateTime(DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[9]), DateUtil.DATE_FORMAT));
                job.setModifiedTime(DateUtil.formatDatePattern(ValueUtil.getDateByObject(obj[10]), DateUtil.DATE_FORMAT));
                job.setStatus(ValueUtil.getIntegerByObject(obj[11]));
                jobs.add(job);
            }
        }
        return jobs;
    }

//    @Override
//    public int countSearchByUserName(String userName, JobUserSearchDto dto) {
//        StringBuilder sb = new StringBuilder();
//        sb.append(" SELECT COUNT(j.JOB_ID) ");
//        appendQueryByUserName(sb,dto);
//        Query query = createQueryByUserName(sb,dto,userName);
//        return ValueUtil.getIntegerByObject(query.getSingleResult());
//        return 0;
//    }


//    @Override
//    public int countSearchRpByUserName(String userName, JobUserSearchDto jobUserSearchDto) {
//        return 0;
//    }


    public void appendQueryByUserName(StringBuilder sb, JobUserSearchDto dto) {
        sb.append(" FROM job j WHERE j.USERNAME = :userName ");
//        if (dto.getKeyword() != null) {
//            sb.append(" AND (j.NAME LIKE :keyword)");
//        }
//        if (dto.getStatus() != null) {
//            sb.append(" AND j.STATUS =:status");
//        }
    }


    public Query createQueryByUserName(StringBuilder sb, JobUserSearchDto dto, String userName) {
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("userName", userName);
//        if (dto.getKeyword() != null) {
//            query.setParameter("keyword", "%" + dto.getKeyword().trim() + "%");
//        }
//        if (dto.getStatus() != null) {
//            query.setParameter("status", dto.getStatus());
//        }
        return query;
    }
}
