package com.looslidias.playlistsuggestion.model.suggestion;

import com.looslidias.playlistsuggestion.model.playlist.dto.PlaylistDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.looslidias.playlistsuggestion.model.wheater.dto.WeatherDTO;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlaylistSuggestionDTO implements Serializable {
    private static final long serialVersionUID = -7748235646550667481L;
    private WeatherDTO weather;
    private List<PlaylistDTO> playlists;
}
