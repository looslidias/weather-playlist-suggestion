package com.looslidias.playlistsuggestion.ws.controller;

import com.looslidias.playlistsuggestion.core.service.music.MusicService;
import com.looslidias.playlistsuggestion.model.music.MusicDTO;
import com.looslidias.playlistsuggestion.ws.controller.mapping.MusicURLMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class MusicController {

    @Autowired
    private MusicService musicService;

    @RequestMapping(value = MusicURLMapping.GENRE_TRACKS_ENDPOINT, method = RequestMethod.GET)
    public ResponseEntity getGenreTracks(@PathVariable("genre") String genre) {
        try {
            MusicDTO response = musicService.searchGenreTracks(genre);
            log.info("Successfully processed Genre Tracks request. Genre: {}", genre);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Bad request for Genre Tracks. Genre: {}", genre, e);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error while processing request for Genre Tracks. Genre: {}", genre, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }
}
