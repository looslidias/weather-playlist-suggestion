package com.looslidias.playlistsuggestion.worker.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.looslidias.playlistsuggestion.core.service.playlist.PlaylistSuggestionService;
import com.looslidias.playlistsuggestion.model.queue.PlaylistCitySuggestionQueueDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Slf4j
@Service
public class PlaylistCitySuggestionListener {

    private PlaylistSuggestionService playlistSuggestionService;
    private ObjectMapper objectMapper;

    public PlaylistCitySuggestionListener(PlaylistSuggestionService playlistSuggestionService, ObjectMapper objectMapper) {
        this.playlistSuggestionService = playlistSuggestionService;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${queue.activemq.playlist-city-suggestion-queue}")
    public void onMessage(String message) {
        try {
            PlaylistCitySuggestionQueueDTO dto = objectMapper.readValue(message, PlaylistCitySuggestionQueueDTO.class);
            playlistSuggestionService.processPlaylistByCity(dto);
        } catch (Exception ex) {
            log.error("Could not process PlaylistCitySuggestionQueueDTO. Message: {}", message, ex);
        }
    }
}
