package com.looslidias.playlistsuggestion.core.properties.queue;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@ConfigurationProperties(prefix = "queue.activemq")
@NoArgsConstructor
@Data
@Component
public class ActiveMQProperties {
    private String brokerUrl;
    private String user;
    private String password;
    private String playlistCitySuggestionQueue;
    private String playlistLatLongSuggestionQueue;
}
