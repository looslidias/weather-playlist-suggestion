package com.looslidias.playlistsuggestion.core.utils;

import com.looslidias.playlistsuggestion.model.music.ArtistDTO;
import com.looslidias.playlistsuggestion.model.music.MusicDTO;
import com.looslidias.playlistsuggestion.model.music.TrackDTO;
import com.looslidias.playlistsuggestion.model.music.spotify.SpotifyArtistDTO;
import com.looslidias.playlistsuggestion.model.music.spotify.SpotifyResponseDTO;
import com.looslidias.playlistsuggestion.model.music.spotify.SpotifyTrackItemDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class MusicUtils {
    private MusicUtils() {
    }

    public static MusicDTO buildMusicResponse(final String genre, final SpotifyResponseDTO response) {
        return MusicDTO.builder()
                .genre(genre)
                .tracks(buildTracksResponse(response.getTracks().getItems()))
                .build();
    }

    private static List<TrackDTO> buildTracksResponse(final List<SpotifyTrackItemDTO> items) {
        return items.stream().map(item -> TrackDTO.builder()
                .name(item.getName())
                .url(item.getExternalUrl().getUrl())
                .popularity(item.getPopularity())
                .artists(buildArtistsResponse(item.getArtists()))
                .build()).collect(Collectors.toList());
    }

    private static List<ArtistDTO> buildArtistsResponse(final List<SpotifyArtistDTO> artists) {
        return artists.stream().map(artist -> ArtistDTO.builder()
                .name(artist.getName())
                .url(artist.getExternalUrl().getUrl())
                .build()).collect(Collectors.toList());
    }
}
