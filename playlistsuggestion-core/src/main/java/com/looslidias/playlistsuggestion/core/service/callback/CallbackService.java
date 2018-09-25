package com.looslidias.playlistsuggestion.core.service.callback;

import com.looslidias.playlistsuggestion.model.callback.CallbackDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Slf4j
@Service
public class CallbackService {

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
    }

    public void sendCallback(final String callbackUrl, final CallbackDTO callback) {
        try {
            restTemplate.postForObject(callbackUrl, callback, String.class);
            log.info("Successfully posted callback. Url: {}. Payload: {}", callbackUrl, callback);
        } catch (Exception ex) {
            log.error("Could not POST callback. Url: {}. Payload: {}", callbackUrl, callback, ex);
        }
    }
}
