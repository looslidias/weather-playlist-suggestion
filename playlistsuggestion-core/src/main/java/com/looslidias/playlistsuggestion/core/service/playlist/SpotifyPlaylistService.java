package com.looslidias.playlistsuggestion.core.service.playlist;

import com.google.common.base.Preconditions;
import com.looslidias.playlistsuggestion.core.properties.playlist.PlaylistProperties;
import com.looslidias.playlistsuggestion.core.utils.PlaylistUtils;
import com.looslidias.playlistsuggestion.model.playlist.Playlist;
import com.looslidias.playlistsuggestion.model.playlist.dao.PlaylistRepository;
import com.looslidias.playlistsuggestion.model.playlist.dto.PlaylistDTO;
import com.looslidias.playlistsuggestion.model.playlist.dto.spotify.SpotifyResponseDTO;
import org.apache.commons.text.StrSubstitutor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Service
public class SpotifyPlaylistService implements PlaylistService {

    private PlaylistProperties playlistProperties;
    private PlaylistRepository playlistRepository;
    private RestTemplate restTemplate;

    public SpotifyPlaylistService(PlaylistProperties playlistProperties, PlaylistRepository playlistRepository) {
        this.playlistProperties = playlistProperties;
        this.playlistRepository = playlistRepository;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Firstly, tries to retrieve information from cache (Redis);
     * Secondly, if there is no cache hot, tries to reach Vendor (Spotify);
     * Finally, if Vendor does not response as expected, tries to gather data from database.
     * @param genre Analysed genre.
     * @return {@link PlaylistDTO}
     */
    @Cacheable(value = "suggestion-tracks", key = "#genre", unless="#result == null")
    @Override
    public PlaylistDTO searchGenreTracks(final String genre) {
        Preconditions.checkArgument(genre != null, "Genre must be specified");

        String url = buildGenreRequestUrl(genre);
        HttpHeaders headers = buildRequestHeaders();
        ResponseEntity<SpotifyResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(headers), SpotifyResponseDTO.class);

        Playlist playlist;
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            playlist = playlistRepository.save(PlaylistUtils.buildPlaylist(genre, response.getBody()));
        } else {
            playlist = playlistRepository.findByGenre(genre);
        }
        return PlaylistUtils.playlistFromModel(playlist);
    }

    private HttpHeaders buildRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + playlistProperties.getSpotify().getAppKey());
        return headers;
    }

    private String buildGenreRequestUrl(final String genre) {
        Map<String, String> substitutes = new HashMap<>();
        substitutes.put("genre", genre);
        substitutes.put("limit", String.valueOf(playlistProperties.getSpotify().getSearchGenreTrackLimit()));

        StrSubstitutor sub = new StrSubstitutor(substitutes);
        return sub.replace(playlistProperties.getSpotify().getSearchGenreTrackUrl());
    }
}
