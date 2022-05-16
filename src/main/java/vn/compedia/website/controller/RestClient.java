package vn.compedia.website.controller;

import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

public class RestClient extends RestTemplate implements Serializable {

    public <T> T postFormDataParam(String url, Object request, Class<T> cls) {
//        logger.debug("Send POST request restUrl: " + url);
//        logger.debug("Send POST data: " + request);
        return this.postForObject(url, request, cls);
    }

}
