package com.looslidias.playlistsuggestion.core.service.playlist.genre;

import com.looslidias.playlistsuggestion.model.wheater.WeatherDTO;

import java.util.List;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public interface PlaylistGenreService {

    /**
     * Searches playlist genres according to given {@code weather}.
     * @param weather Analysed {@link WeatherDTO}.
     * @return A List of playlsist genres
     */
    List<String> searchGenres(final WeatherDTO weather);
}