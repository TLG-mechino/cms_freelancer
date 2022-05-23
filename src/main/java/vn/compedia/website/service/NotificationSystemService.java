package vn.compedia.website.service;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import vn.compedia.website.controller.RestClient;
import vn.compedia.website.dto.LoginDTO;
import vn.compedia.website.model.Account;
import vn.compedia.website.model.Notification;
import vn.compedia.website.model.NotificationRef;
import vn.compedia.website.repository.AccountRepository;
import vn.compedia.website.repository.NotificationRefRepository;
import vn.compedia.website.repository.NotificationRepository;
import vn.compedia.website.repository.RegisterNotificationRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Service
public class NotificationSystemService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private NotificationRefRepository notificationRefRepository;
    @Autowired
    private RegisterNotificationRepository registerNotificationRepository;

    @Value("${api.notifications}")
    private String url;

    @Value("${api.login}")
    private String urlLogin;

    public Long accountId;

    public void saveNotification(String sender, String title, String content, Integer type, Long objectId, List<String> usernameList) {
        Notification notification = new Notification(content, new Timestamp(new Date().getTime()), sender, 1, title, objectId, type);
        notificationRepository.save(notification);

        List<NotificationRef> notificationRefList = new ArrayList<>();
        for (String i : usernameList) {
            NotificationRef notificationRef = new NotificationRef();
            notificationRef.setNotificationId(notification.getId());
            notificationRef.setUsername(i);
            notificationRef.setStatus(0);
            notificationRefList.add(notificationRef);
        }
        notificationRefRepository.saveAll(notificationRefList);
        pushNotification(notification.getId());
    }

    public void pushNotification(Long notificationId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Account account = accountRepository.findById(accountId).orElse(null);
        if (null == account) {
            return;
        }
        headers.add("Authorization", "Bearer " + account.getToken());
        HttpEntity<Long> request = new HttpEntity<>(headers);
        RestClient restClient = new RestClient();
        restClient.postFormData(url + "/" + notificationId, request, Object.class);
    }

    public void loginApi(LoginDTO loginDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject object = new JSONObject();
        object.put("username", loginDTO.getUsername());
        object.put("password", loginDTO.getPassword());

//        HttpEntity<String> request = new HttpEntity<>(object.toString(), headers);
//        RestClient restClient = new RestClient();
//        restClient.postFormData(urlLogin, request, Object.class);

        HttpEntity<Object> request = new HttpEntity<>(object.toString(), headers);
        RestClient restClient = new RestClient();
        restClient.postFormJson(urlLogin, request, Object.class);
    }
}
