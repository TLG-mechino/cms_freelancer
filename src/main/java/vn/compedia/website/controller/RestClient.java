package vn.compedia.website.controller;

import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

public class RestClient extends RestTemplate implements Serializable {

    public <T> T postFormData(String url, Object request, Class<T> cls) {
        logger.info("Send POST request url: " + url);
        logger.info("Send POST data: " + request);
        return this.postForObject(url, request, cls);
    }

    public <T> T postFormJson(String url, Object request, Class<T> cls) {
        logger.info("Send POST request url: " + url);
        logger.info("Send POST data: " + request);
        return (T) this.postForEntity(url, request, cls);
    }
}
