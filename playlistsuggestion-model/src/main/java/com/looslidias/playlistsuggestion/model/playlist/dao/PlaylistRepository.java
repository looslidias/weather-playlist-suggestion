package com.looslidias.playlistsuggestion.model.playlist.dao;

import com.looslidias.playlistsuggestion.model.playlist.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 25/09/18
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
    Playlist findByGenre(String genre);
}
