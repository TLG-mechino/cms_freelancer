package vn.compedia.website.util;

import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import vn.compedia.website.properties.EnvironmentProperties;

import java.io.Serializable;

public class RestClient extends RestTemplate implements Serializable {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private String API_DOMAIN = EnvironmentProperties.getData(ApiUrlKey.KEY_DOMAIN);

    public <T> T postRequestJson(String apiResourceKey, JsonObject jsonData, Class<T> cls) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        String url = EnvironmentProperties.getData(apiResourceKey);
        String restUrl = API_DOMAIN + url;

        logger.info("Send post request restUrl: " + url);
        logger.info("Send post data: " + jsonData);

        HttpEntity<String> entity = new HttpEntity<>(jsonData.toString(), requestHeaders);
        return this.postForObject(restUrl, entity, cls);
    }

    public <T> T getRequestJson(String apiResourceKey, JsonObject jsonData, Class<T> cls) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        String url = EnvironmentProperties.getData(apiResourceKey);
        String restUrl = API_DOMAIN + url;

        logger.info("Send GET request restUrl: " + url);
        logger.info("Send GET data: " + jsonData);

        HttpEntity<Object> entity = new HttpEntity<>(jsonData, requestHeaders);
        return this.getForObject(restUrl, cls, entity);
    }

    public <T> T putRequest(String apiResourceKey, Object object, Class<T> cls) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        String url = EnvironmentProperties.getData(apiResourceKey);
        String restUrl = API_DOMAIN + url;

        logger.info("Send PUT request restUrl: " + url);
        logger.info("Send PUT data: " + object);

        HttpEntity<Object> entity = new HttpEntity<Object>(object, requestHeaders);
        ResponseEntity<T> result = this.exchange(restUrl, HttpMethod.PUT, entity, cls);

        return result.getBody();
    }

    public <T> T deleteRequest(String apiResourceKey, Object object, Class<T> cls) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        String url = EnvironmentProperties.getData(apiResourceKey);
        String restUrl = API_DOMAIN + url;

        logger.info("Send DELETE request restUrl: " + url);
        logger.info("Send DELETE data: " + object);

        HttpEntity<Object> entity = new HttpEntity<Object>(object, requestHeaders);
        ResponseEntity<T> result = this.exchange(restUrl, HttpMethod.DELETE, entity, cls);

        return result.getBody();
    }
}
