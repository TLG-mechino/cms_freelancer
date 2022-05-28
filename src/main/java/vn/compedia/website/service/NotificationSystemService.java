package vn.compedia.website.service;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@Service
public class NotificationSystemService {
    private static Logger logger = LoggerFactory.getLogger(NotificationSystemService.class);

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

    @Value("${api.loginBackend}")
    private String urlLogin;

    public Long accountId;
    public String token;
    public LoginDTO loginDTO;

    public void saveNotification(String sender, String titleVn, String contentVn, String titleEn, String contentEn, Integer type, Long objectId, List<String> usernameList) {
        Notification notification = new Notification(contentVn, new Timestamp(new Date().getTime()), sender, 1, objectId, titleVn, titleEn, contentEn, type);
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
        token = loginApi(loginDTO);
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<Long> request = new HttpEntity<>(headers);
        RestClient restClient = new RestClient();
        restClient.postFormData(url + "/" + notificationId, request, Object.class);
    }

    public String loginApi(LoginDTO loginDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject object = new JSONObject();
        object.put("username", loginDTO.getUsername());
        object.put("password", loginDTO.getPassword());

        HttpEntity<Object> request = new HttpEntity<>(object.toString(), headers);
        RestClient restClient = new RestClient();
        ResponseEntity<?> response = (ResponseEntity<?>) restClient.postFormJson(urlLogin, request, Object.class);

        String token = null;
        try {
            if (response.getStatusCode() != HttpStatus.OK) {
                logger.error("Have an error!");
            }
            LinkedHashMap<String, Object> responseMap = (LinkedHashMap<String, Object>) response.getBody();
            LinkedHashMap<String, String> tokenMap = (LinkedHashMap<String, String>) responseMap.get("data");
            token = tokenMap.get("access_token");
        } catch (Exception exception) {
            logger.error("Cannot cast value from response!");
        }

        return token;
    }
}