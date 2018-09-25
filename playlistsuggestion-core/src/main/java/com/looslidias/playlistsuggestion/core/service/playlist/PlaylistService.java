package com.looslidias.playlistsuggestion.core.service.music;

import com.looslidias.playlistsuggestion.model.playlist.dto.PlaylistDTO;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public interface MusicService {

    /**
     * Searches playlist tracks according to given {@code genre}.
     * @param genre Analysed genre.
     * @return {@link PlaylistDTO} containing track details
     */
    PlaylistDTO searchGenreTracks(final String genre);
}
