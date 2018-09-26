package com.looslidias.playlistsuggestion.ws.controller;

import com.looslidias.playlistsuggestion.core.service.suggestion.PlaylistSuggestionService;
import com.looslidias.playlistsuggestion.model.suggestion.PlaylistSuggestionCityRequest;
import com.looslidias.playlistsuggestion.model.suggestion.PlaylistSuggestionDTO;
import com.looslidias.playlistsuggestion.model.suggestion.PlaylistSuggestionLatLongRequest;
import com.looslidias.playlistsuggestion.ws.controller.mapping.PlaylistSuggestionMapping;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Slf4j
@RestController
public class PlaylistSuggestionController {

    @Autowired
    private PlaylistSuggestionService playlistSuggestionService;

    @RequestMapping(value = PlaylistSuggestionMapping.PLAYLIST_CITY_ENDPOINT, method = RequestMethod.GET)
    @ApiOperation(value = "Get weather suggestion suggestion regarding city input synchronously", nickname = "Get weather suggestion suggestion regarding city input synchronously")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity getSuggestionByCity(@PathVariable("city") String city, @PathVariable("countryCode") String countryCode) {
        try {
            PlaylistSuggestionDTO response = playlistSuggestionService.searchPlaylistByCity(city, countryCode);
            log.info("Successfully processed synchronous City Playlist Suggestion request. City: {}, CountryCode: {}", city, countryCode);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Bad request for synchronous City Playlist Suggestion. City: {}, CountryCode: {}", city, countryCode, e);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error while processing synchronous request for City Playlist Suggestion. City: {}, CountryCode: {}", city, countryCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }

    @RequestMapping(value = PlaylistSuggestionMapping.PLAYLIST_LAT_LONG_ENDPOINT, method = RequestMethod.GET)
    @ApiOperation(value = "Get weather suggestion suggestion regarding latitude/longitude input synchronously", nickname = "Get weather suggestion suggestion regarding city input synchronously")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity getSuggestionByLatLong(@PathVariable("lat") Double lat, @PathVariable("long") Double lon) {
        try {
            PlaylistSuggestionDTO response = playlistSuggestionService.searchPlaylistByLatLong(lat, lon);
            log.info("Successfully processed synchronous LatLong Playlist Suggestion request. Lat: {}, Long: {}", lat, lon);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (IllegalArgumentException e) {
            log.error("Bad request for synchronous LatLong Playlist Suggestion. Lat: {}, Long: {}", lat, lon, e);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error while processing request for synchronous LatLong Playlist Suggestion. Lat: {}, Long: {}", lat, lon, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }

    @RequestMapping(value = PlaylistSuggestionMapping.PLAYLIST_CITY_ENDPOINT_ASYNC, method = RequestMethod.POST)
    @ApiOperation(value = "POST weather suggestion suggestion request regarding city", nickname = "POST weather suggestion suggestion regarding city")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity postCityPlaylistRequest(@RequestBody PlaylistSuggestionCityRequest request) {
        try {
            playlistSuggestionService.schedulePlaylistByCity(request);
            log.info("Successfully processed asynchronous City Playlist Suggestion request: {}", request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("Bad request for asynchronous City Playlist Suggestion. Request {}", request, e);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error while processing request for asynchronous City Playlist Suggestion. Request: {}", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }

    @RequestMapping(value = PlaylistSuggestionMapping.PLAYLIST_LAT_LONG_ENDPOINT_ASYNC, method = RequestMethod.POST)
    @ApiOperation(value = "POST weather suggestion suggestion request regarding latitude/longitude", nickname = "Get weather suggestion suggestion regarding city input asynchronously")
    @ApiResponses(value =
            {@ApiResponse(code = 200, message = "Success"), @ApiResponse(code = 400, message = "Bad Request"),
                    @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Internal Server Error")})
    public ResponseEntity postLatLongPlaylistRequest(@RequestBody PlaylistSuggestionLatLongRequest request) {
        try {
            playlistSuggestionService.schedulePlaylistByLatLong(request);
            log.info("Successfully processed asynchronous LatLong Playlist Suggestion request: {}", request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            log.error("Bad request for asynchronous LatLong Playlist Suggestion. Request: {}", request, e);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        } catch (Exception e) {
            log.error("Error while processing request for asynchronous LatLong Playlist Suggestion. Request: {}", request, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
        }
    }
}
