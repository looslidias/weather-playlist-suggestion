package com.looslidias.playlistsuggestion.core.service.playlist;

import com.looslidias.playlistsuggestion.core.properties.playlist.PlaylistProperties;
import com.looslidias.playlistsuggestion.model.playlist.dto.spotify.SpotifyCredentialsResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 26/09/18
 */
@Slf4j
@Service
public class SpotifyCredentialsService {

    private PlaylistProperties playlistProperties;
    private RestTemplate restTemplate;
    private String accessToken;

    @Autowired
    public SpotifyCredentialsService(PlaylistProperties playlistProperties) {
        this.playlistProperties = playlistProperties;
        this.restTemplate = new RestTemplate();
    }

    @PostConstruct
    public void init() throws UnsupportedEncodingException {
        refreshToken();
    }

    /**
     * Refreshes Spotify token every 30min, as it expires in 60min
     * @throws UnsupportedEncodingException
     */
    @Scheduled(fixedRate = 1800000)
    private void refreshToken() throws UnsupportedEncodingException {
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(buildAccessTokenPayload(), buildAccessTokenHeaders());
        ResponseEntity<SpotifyCredentialsResponseDTO> response = restTemplate.postForEntity(playlistProperties.getSpotify().getCredentialsUrl(),
                request ,SpotifyCredentialsResponseDTO.class);

        if (!HttpStatus.OK.equals(response.getStatusCode())) {
            log.error("Could not refresh Spotify Access Token");
            throw new IllegalStateException("Could not refresh Spotify Access Token");
        }

        accessToken = response.getBody().getAccessToken();
        log.info("Successfully refreshed Spotify Access Token");
    }

    private MultiValueMap<String, String> buildAccessTokenPayload() {
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        return map;
    }

    private HttpHeaders buildAccessTokenHeaders() throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        String credentials = playlistProperties.getSpotify().getClientId() + ":" + playlistProperties.getSpotify().getClientSecret();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes("utf-8")));
        headers.set(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
        return headers;
    }

    public String getAccessToken() {
        return this.accessToken;
    }
}
