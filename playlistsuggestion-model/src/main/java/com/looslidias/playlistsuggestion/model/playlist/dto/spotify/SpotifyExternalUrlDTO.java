package com.looslidias.playlistsuggestion.model.playlist.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpotifyExternalUrlDTO {
    @JsonProperty("spotify")
    private String url;
}
