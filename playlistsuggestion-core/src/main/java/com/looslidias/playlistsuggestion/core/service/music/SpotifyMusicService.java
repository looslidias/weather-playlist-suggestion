package com.looslidias.playlistsuggestion.core.service.music;

import com.google.common.base.Preconditions;
import com.looslidias.playlistsuggestion.core.properties.music.MusicProperties;
import com.looslidias.playlistsuggestion.core.utils.MusicUtils;
import com.looslidias.playlistsuggestion.model.music.MusicDTO;
import com.looslidias.playlistsuggestion.model.music.spotify.SpotifyResponseDTO;
import org.apache.commons.text.StrSubstitutor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Service
public class SpotifyMusicService implements MusicService {

    private MusicProperties musicProperties;
    private RestTemplate restTemplate;

    public SpotifyMusicService(MusicProperties musicProperties) {
        this.musicProperties = musicProperties;
        this.restTemplate = new RestTemplate();
    }

    @Cacheable(value = "music-tracks", key = "#genre")
    @Override
    public MusicDTO searchGenreTracks(String genre) {
        Preconditions.checkArgument(genre != null, "Genre must be specified");

        String url = buildGenreRequestUrl(genre);
        HttpHeaders headers = buildRequestHeaders();

        ResponseEntity<SpotifyResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(headers), SpotifyResponseDTO.class);
        return MusicUtils.buildMusicResponse(genre, response.getBody());
    }

    private HttpHeaders buildRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + musicProperties.getSpotify().getAppKey());
        return headers;
    }

    private String buildGenreRequestUrl(final String genre) {
        Map<String, String> substitutes = new HashMap<>();
        substitutes.put("genre", genre);
        substitutes.put("limit", String.valueOf(musicProperties.getSpotify().getSearchGenreTrackLimit()));

        StrSubstitutor sub = new StrSubstitutor(substitutes);
        return sub.replace(musicProperties.getSpotify().getSearchGenreTrackUrl());
    }
}
