package vn.compedia.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import vn.compedia.website.model.NotificationRef;
import vn.compedia.website.repository.NotificationRefRepository;
import vn.compedia.website.service.NotificationRefService;

import java.util.List;

public class NotificationRefServiceImpl implements NotificationRefService {

    @Autowired
    NotificationRefRepository notificationRefRepository;


    @Override
    public List<NotificationRef> getAllByUserName (String username) throws Exception {
        try{
           return notificationRefRepository.getAllByUserName(username);
        } catch (Exception e){
            throw new Exception("Could not get data");
        }
    }

    @Override
    public NotificationRef getNotificationRefById(Long id) {
        if (notificationRefRepository.getNotificationRefById(id)!=null) {
            return notificationRefRepository.getNotificationRefById(id);
        }
        return null;
    }
}
