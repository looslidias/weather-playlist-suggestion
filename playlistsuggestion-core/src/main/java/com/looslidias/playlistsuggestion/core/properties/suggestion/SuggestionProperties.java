package com.looslidias.playlistsuggestion.core.properties.suggestion;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@ConfigurationProperties(prefix = "suggestion")
@NoArgsConstructor
@Data
@Component
public class SuggestionProperties {
    private String genreRulesFile;
}
