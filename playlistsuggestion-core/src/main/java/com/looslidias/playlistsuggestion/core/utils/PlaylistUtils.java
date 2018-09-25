package com.looslidias.playlistsuggestion.core.utils;

import com.looslidias.playlistsuggestion.model.playlist.Artist;
import com.looslidias.playlistsuggestion.model.playlist.Playlist;
import com.looslidias.playlistsuggestion.model.playlist.Track;
import com.looslidias.playlistsuggestion.model.playlist.dto.ArtistDTO;
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
public final class MusicUtils {

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
                .artists(buildArtists(item.getArtists()))
                .build()).collect(Collectors.toList());
    }

    private static List<Artist> buildArtists(final List<SpotifyArtistDTO> artists) {
        return artists.stream().map(artist -> Artist.builder()
                .name(artist.getName())
                .url(artist.getExternalUrl().getUrl())
                .build()).collect(Collectors.toList());
    }

    private static PlaylistDTO playlistFromModel(final Playlist playlist) {
        return PlaylistDTO.builder()
                .genre(playlist.getGenre())
                .tracks(tracksFromModel(playlist.getTracks()))
                .build();
    }

    private static List<TrackDTO> tracksFromModel(final List<Track> tracks) {
        return tracks.stream().map(track -> TrackDTO.builder()
                .name(track.getName())
                .url(track.getUrl())
                .popularity(track.getPopularity())
                .artists(artistsFromModel(track.getArtists()))
                .build()).collect(Collectors.toList());
    }

    private static List<ArtistDTO> artistsFromModel(List<Artist> artists) {
        return artists.stream().map(artist -> ArtistDTO.builder()
                .name(artist.getName())
                .url(artist.getUrl())
                .build()).collect(Collectors.toList());
    }

}
