package com.looslidias.playlistsuggestion.core.utils;

import com.looslidias.playlistsuggestion.model.playlist.PlaylistSuggestionDTO;
import com.looslidias.playlistsuggestion.model.music.MusicDTO;
import com.looslidias.playlistsuggestion.model.wheater.WeatherDTO;

import java.util.List;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class PlaylistSuggestionUtils {
    private PlaylistSuggestionUtils() {
    }

    public static PlaylistSuggestionDTO buildPlaylistSuggestionResponse(final WeatherDTO weatherDTO, final List<MusicDTO> playlists) {
        return PlaylistSuggestionDTO.builder()
                .weather(weatherDTO)
                .playlists(playlists)
                .build();
    }
}
