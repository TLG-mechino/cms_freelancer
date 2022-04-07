package vn.compedia.website.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import vn.compedia.website.dto.JobDto;
import vn.compedia.website.dto.JobSearchDto;
import vn.compedia.website.dto.NotificationDto;
import vn.compedia.website.dto.NotificationSearchDto;
import vn.compedia.website.repository.NotificationRepositoryCustom;
import vn.compedia.website.util.ValueUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<NotificationDto> getAllNotificationRpByUserName(String username, NotificationSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" select n.NOTIFICATION_ID, " +
                "       n.CONTENT, " +
                "       n.SENDING_TIME, " +
                "       n.USERNAME, " +
                "       n.STATUS ");
        appendQueryByUserName(sb, searchDto, username);
        if (searchDto.getSortField() != null) {
            if (searchDto.getSortField().equals("content")) {
                sb.append(" ORDER BY n.CONTENT");
            }
            if (searchDto.getSortField().equals("username")) {
                sb.append(" ORDER BY n.USERNAME");
            }
            if (searchDto.getSortField().equals("sendingTime")) {
                sb.append(" ORDER BY n.SENDING_TIME");
            }
            if (searchDto.getSortField().equals("status")) {
                sb.append(" ORDER BY n.STATUS");
            }
            sb.append(searchDto.getSortOrder());
        } else {
            sb.append(" ORDER BY n.NOTIFICATION_ID DESC");
        }
        Query query = createQueryByUserName(sb, searchDto, username);

        if (searchDto.getPageSize() > 0) {
            query.setFirstResult(searchDto.getPageIndex());
            query.setMaxResults(searchDto.getPageSize());
        } else {
            query.setFirstResult(0);
            query.setMaxResults(Integer.MAX_VALUE);
        }
        List<Object[]> resultList = query.getResultList();
        List<NotificationDto> notificationDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(resultList)) {
            for (Object[] obj : resultList) {
                NotificationDto notification = new NotificationDto();
                notification.setId(ValueUtil.getLongByObject(obj[0]));
                notification.setContent(ValueUtil.getStringByObject(obj[1]));
                notification.setSendingTime(ValueUtil.getDateByObject(obj[2]));
                notification.setUsername(ValueUtil.getStringByObject(obj[3]));
                notification.setStatus(ValueUtil.getIntegerByObject(obj[4]));
                notificationDtos.add(notification);
            }
        }
        return notificationDtos;
    }

    @Override
    public BigInteger countSearchRpByUserName(String username, NotificationSearchDto searchDto) {
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT COUNT(n.NOTIFICATION_ID) ");
        appendQueryByUserName(sb,searchDto, username);
        Query query = createQueryByUserName(sb,searchDto,username);
        return (BigInteger) query.getSingleResult();
    }

    public void appendQueryByUserName(StringBuilder sb, NotificationSearchDto searchDto, String username) {
        sb.append(" FROM notification n WHERE n.USERNAME = :username ");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("username", username);
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            sb.append(" AND lower(n.CONTENT) LIKE lower(:keyword)) " );
        }
        if (searchDto.getStatus() != null) {
            sb.append(" AND j.STATUS =:status ");
        }
    }


    public Query createQueryByUserName(StringBuilder sb, NotificationSearchDto searchDto, String username) {
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("username", username);
        if (StringUtils.isNotBlank(searchDto.getKeyword())) {
            query.setParameter("keyword", "%" + searchDto.getKeyword().trim() + "%");
        }
        if (searchDto.getStatus() != null) {
            query.setParameter("status", searchDto.getStatus());
        }
        return query;
    }
}
