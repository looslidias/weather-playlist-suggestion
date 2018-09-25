package com.looslidias.playlistsuggestion.model.music.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpotifyTrackItemDTO {
    private String name;
    private Float popularity;
    private List<SpotifyArtistDTO> artists;
    @JsonProperty("external_urls")
    private SpotifyExternalUrlDTO externalUrl;
}
