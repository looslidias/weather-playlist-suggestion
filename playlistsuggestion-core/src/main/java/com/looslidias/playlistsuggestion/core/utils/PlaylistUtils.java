package com.looslidias.playlistsuggestion.core.utils;

import com.looslidias.playlistsuggestion.model.playlist.Playlist;
import com.looslidias.playlistsuggestion.model.playlist.Track;
import com.looslidias.playlistsuggestion.model.playlist.dto.PlaylistDTO;
import com.looslidias.playlistsuggestion.model.playlist.dto.TrackDTO;
import com.looslidias.playlistsuggestion.model.playlist.dto.spotify.SpotifyArtistDTO;
import com.looslidias.playlistsuggestion.model.playlist.dto.spotify.SpotifyResponseDTO;
import com.looslidias.playlistsuggestion.model.playlist.dto.spotify.SpotifyTrackItemDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class PlaylistUtils {

    public static Playlist buildPlaylist(final String genre, final SpotifyResponseDTO response) {
        return Playlist.builder()
                .genre(genre)
                .tracks(buildTracks(response.getTracks().getItems()))
                .build();
    }

    private static List<Track> buildTracks(final List<SpotifyTrackItemDTO> items) {
        return items.stream().map(item -> Track.builder()
                .name(item.getName())
                .url(item.getExternalUrl().getUrl())
                .popularity(item.getPopularity())
                .artists(item.getArtists().stream().map(SpotifyArtistDTO::getName).collect(Collectors.toList()))
                .build()).collect(Collectors.toList());
    }

    public static PlaylistDTO playlistFromModel(final Playlist playlist) {
        if (playlist == null) {
            return null;
        }
        return PlaylistDTO.builder()
                .genre(playlist.getGenre())
                .tracks(tracksFromModel(playlist.getTracks()))
                .updatedAt(playlist.getUpdatedAt())
                .build();
    }

    private static List<TrackDTO> tracksFromModel(final List<Track> tracks) {
        return tracks.stream().map(track -> TrackDTO.builder()
                .name(track.getName())
                .url(track.getUrl())
                .popularity(track.getPopularity())
                .artists(track.getArtists())
                .build()).collect(Collectors.toList());
    }

}
