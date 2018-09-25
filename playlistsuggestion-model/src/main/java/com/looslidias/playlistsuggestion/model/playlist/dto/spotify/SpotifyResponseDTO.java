package com.looslidias.playlistsuggestion.model.music.dto.spotify;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SpotifyResponseDTO {
    private SpotifyTrackDTO tracks;
}
