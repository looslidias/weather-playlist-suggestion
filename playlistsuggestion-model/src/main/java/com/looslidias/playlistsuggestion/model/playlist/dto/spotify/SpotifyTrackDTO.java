package com.looslidias.playlistsuggestion.model.playlist.dto.spotify;

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
public class SpotifyTrackDTO {
    private List<SpotifyTrackItemDTO> items;
}
