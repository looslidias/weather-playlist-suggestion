package com.looslidias.playlistsuggestion.core.service.music;

import com.looslidias.playlistsuggestion.model.music.MusicDTO;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public interface MusicService {

    /**
     * Searches music tracks according to given {@code genre}.
     * @param genre Analysed genre.
     * @return {@link MusicDTO} containing track details
     */
    MusicDTO searchGenreTracks(final String genre);
}
