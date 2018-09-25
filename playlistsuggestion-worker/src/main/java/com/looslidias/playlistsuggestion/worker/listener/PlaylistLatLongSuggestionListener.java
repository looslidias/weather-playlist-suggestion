package com.looslidias.playlistsuggestion.worker.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.looslidias.playlistsuggestion.core.service.playlist.PlaylistSuggestionService;
import com.looslidias.playlistsuggestion.model.queue.PlaylistLatLongSuggestionQueueDTO;
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
            PlaylistLatLongSuggestionQueueDTO dto = objectMapper.readValue(message, PlaylistLatLongSuggestionQueueDTO.class);
            playlistSuggestionService.processPlaylistByLatLong(dto);
        } catch (Exception ex) {
            log.error("Could not process PlaylistLatLongSuggestionQueueDTO. Message: {}", message, ex);
        }
    }
}
