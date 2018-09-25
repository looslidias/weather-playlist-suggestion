package com.looslidias.playlistsuggestion.core.properties.music;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@NoArgsConstructor
@Data
public class SpotifyProperties {
    private String searchGenreTrackUrl;
    private Integer searchGenreTrackLimit;
    private String appKey;
}
