package vn.compedia.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import vn.compedia.website.controller.AuthorizationController;
import vn.compedia.website.controller.RestClient;
import vn.compedia.website.dto.AccountDto;
import vn.compedia.website.model.Account;
import vn.compedia.website.model.Notification;
import vn.compedia.website.model.NotificationRef;
import vn.compedia.website.repository.AccountRepository;
import vn.compedia.website.repository.NotificationRefRepository;
import vn.compedia.website.repository.NotificationRepository;
import vn.compedia.website.repository.RegisterNotificationRepository;
import vn.compedia.website.util.PropertiesUtil;
import vn.compedia.website.util.TokensCloak;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationSystemService {

    @Autowired
    NotificationRepository notificationRepository;

    public Long accountIdR;

    @Autowired
    NotificationRefRepository notificationRefRepository;

    @Autowired
    RegisterNotificationRepository registerNotificationRepository;

    @Autowired
    AccountRepository accountRepository;

    private String url = PropertiesUtil.getProperty("api.notifications");

    @Transactional
    public void saveNotification(String sender, String title, String content, Integer type, Long objectId, List<String> usernameList) {
        Notification notification = new Notification(content, new Timestamp(new Date().getTime()),
                sender, 0, title, objectId, type);
        notificationRepository.save(notification);

        List<NotificationRef> notificationRefList = new ArrayList<>();
        for (String i: usernameList) {
            NotificationRef notificationRef = new NotificationRef();
            notificationRef.setNotificationId(notification.getId());
            notificationRef.setUsername(i);
            notificationRef.setStatus(0);
            notificationRefList.add(notificationRef);
        }
        notificationRefRepository.saveAll(notificationRefList);
        getToken(notification.getId());
    }

    public void setAccountId(Long accountId){
        accountIdR = accountId;
    }

    public void getToken(Long notificationId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer " + accountRepository.findById(accountIdR).get().getToken());
//        headers.add("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImZ1bGxOYW1lIjoiRG8gRGFuIiwiZW1haWwiOiJkb3RoaXRhbWRhbkBnbWFpbC5jb20iLCJpYXQiOjE2NTI2ODUxMDcsImV4cCI6MTY4NDI0MjcwN30.GzvGN2mKxRzSK77bCFlWM0fhdg5zqs53oY0H3753BDQ");
        MultiValueMap<String, Long> mapParams = new LinkedMultiValueMap<String, Long>();
        mapParams.add("notification_id", notificationId);
        HttpEntity<MultiValueMap<String, Long>> request = new HttpEntity<>(mapParams, headers);
        RestClient restClient = new RestClient();
        TokensCloak tokensCloak = restClient.postFormDataParam(url, request, TokensCloak.class);
    }
}
