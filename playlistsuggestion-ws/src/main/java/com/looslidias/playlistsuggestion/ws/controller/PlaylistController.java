package com.looslidias.playlistsuggestion.ws.controller;

import com.looslidias.playlistsuggestion.core.service.playlist.PlaylistService;
import com.looslidias.playlistsuggestion.model.playlist.dto.PlaylistDTO;
import com.looslidias.playlistsuggestion.ws.controller.mapping.PlaylistURLMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Slf4j
@RestController
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @RequestMapping(value = PlaylistURLMapping.TRACKS_GENRE_ENDPOINT, method = RequestMethod.GET)
    public ResponseEntity getTracks(@PathVariable("genre") String genre) {
        try {
            PlaylistDTO response = playlistService.searchGenreTracks(genre);
            if (response == null) {
                String message = "Could not find any tracks for genre: " + genre;
                log.warn(message);
                return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.TEXT_PLAIN).body(message + ". Please try again later");
            }
            log.info("Successfully processed Tracks request. Genre: {}", genre);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Bad request for Tracks. Genre: {}", genre, e);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error while processing request for Tracks. Genre: {}", genre, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }
}
