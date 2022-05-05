package vn.compedia.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.compedia.website.model.Notification;
import vn.compedia.website.model.NotificationRef;
import vn.compedia.website.repository.NotificationRefRepository;
import vn.compedia.website.repository.NotificationRepository;
import vn.compedia.website.repository.RegisterNotificationRepository;
import vn.compedia.website.util.DbConstant;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationSystemService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    NotificationRefRepository notificationRefRepository;

    @Autowired
    RegisterNotificationRepository registerNotificationRepository;

    @Transactional
    public void saveNotification(String sender, String title, String content, Integer type, Long objectId, List<String> usernameList) {
        Notification notification = new Notification(content, new Timestamp(new Date().getTime()),
                sender, 1, title, objectId, type);
        notificationRepository.save(notification);

        List<NotificationRef> notificationRefList = new ArrayList<>();
        for (String i: usernameList) {
            NotificationRef notificationRef = new NotificationRef();
            notificationRef.setNotificationId(notification.getId());
            notificationRef.setUsername(i);
            notificationRef.setStatus(1);
            notificationRefList.add(notificationRef);
        }

        notificationRefRepository.saveAll(notificationRefList);
    }
}
