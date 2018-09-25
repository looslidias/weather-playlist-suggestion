package com.looslidias.playlistsuggestion.core.properties.weather;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@NoArgsConstructor
@Data
public class OpenWeatherProperties {
    private String cityBaseUrl;
    private String latLongBaseUrl;
    private String appKey;
}
