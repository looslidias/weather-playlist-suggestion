package com.looslidias.playlistsuggestion.core.properties.playlist;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@ConfigurationProperties(prefix = "playlist")
@NoArgsConstructor
@Data
@Component
public class PlaylistProperties {
    private SpotifyProperties spotify;
}
