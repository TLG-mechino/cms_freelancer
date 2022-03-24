package vn.compedia.website.service;

import org.springframework.stereotype.Service;
import vn.compedia.website.model.NotificationRef;

import java.util.List;

@Service
public interface NotificationRefService {

    List<NotificationRef> getAllByUserName(String userName) throws Exception;

    NotificationRef getNotificationRefById(Long id);

}
