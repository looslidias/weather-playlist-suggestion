package com.looslidias.playlistsuggestion.core.utils;

import com.looslidias.playlistsuggestion.model.suggestion.PlaylistSuggestionDTO;
import com.looslidias.playlistsuggestion.model.playlist.dto.PlaylistDTO;
import com.looslidias.playlistsuggestion.model.wheater.dto.WeatherDTO;

import java.util.List;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class PlaylistSuggestionUtils {
    private PlaylistSuggestionUtils() {
    }

    public static PlaylistSuggestionDTO buildPlaylistSuggestionResponse(final WeatherDTO weatherDTO, final List<PlaylistDTO> playlists) {
        return PlaylistSuggestionDTO.builder()
                .weather(weatherDTO)
                .playlists(playlists)
                .build();
    }
}
