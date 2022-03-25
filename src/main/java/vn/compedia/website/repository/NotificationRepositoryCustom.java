package vn.compedia.website.repository;

import vn.compedia.website.dto.NotificationDto;
import vn.compedia.website.dto.NotificationSearchDto;

import java.math.BigInteger;
import java.util.List;

public interface NotificationRepositoryCustom {
    List<NotificationDto> getAllNotificationRpByUserName(String userName, NotificationSearchDto searchDto);

    BigInteger countSearchRpByUserName(String userName, NotificationSearchDto searchDto);
}
