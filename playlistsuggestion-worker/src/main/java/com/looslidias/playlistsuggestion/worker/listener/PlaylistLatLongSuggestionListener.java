package com.looslidias.playlistsuggestion.worker.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.looslidias.playlistsuggestion.core.service.suggestion.PlaylistSuggestionService;
import com.looslidias.playlistsuggestion.model.suggestion.queue.PlaylistSuggestionLatLongQueueDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Slf4j
@Service
public class PlaylistLatLongSuggestionListener {

    private PlaylistSuggestionService playlistSuggestionService;
    private ObjectMapper objectMapper;

    public PlaylistLatLongSuggestionListener(PlaylistSuggestionService playlistSuggestionService, ObjectMapper objectMapper) {
        this.playlistSuggestionService = playlistSuggestionService;
        this.objectMapper = objectMapper;
    }

    @JmsListener(destination = "${queue.activemq.playlist-lat-long-suggestion-queue}")
    public void onMessage(String message) {
        try {
            PlaylistSuggestionLatLongQueueDTO dto = objectMapper.readValue(message, PlaylistSuggestionLatLongQueueDTO.class);
            playlistSuggestionService.processPlaylistByLatLong(dto);
        } catch (Exception ex) {
            log.error("Could not process PlaylistSuggestionLatLongQueueDTO. Message: {}", message, ex);
        }
    }
}
