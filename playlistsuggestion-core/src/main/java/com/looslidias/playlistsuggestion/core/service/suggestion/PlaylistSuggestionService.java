package com.looslidias.playlistsuggestion.core.service.suggestion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.looslidias.playlistsuggestion.core.properties.queue.ActiveMQProperties;
import com.looslidias.playlistsuggestion.core.service.callback.CallbackService;
import com.looslidias.playlistsuggestion.core.service.playlist.PlaylistService;
import com.looslidias.playlistsuggestion.core.service.suggestion.genre.SuggestionGenreService;
import com.looslidias.playlistsuggestion.core.service.weather.WeatherService;
import com.looslidias.playlistsuggestion.core.utils.CallbackUtils;
import com.looslidias.playlistsuggestion.core.utils.PlaylistSuggestionUtils;
import com.looslidias.playlistsuggestion.model.suggestion.queue.PlaylistSuggestionCityQueueDTO;
import com.looslidias.playlistsuggestion.model.suggestion.PlaylistSuggestionCityRequest;
import com.looslidias.playlistsuggestion.model.suggestion.PlaylistSuggestionLatLongRequest;
import com.looslidias.playlistsuggestion.model.suggestion.PlaylistSuggestionDTO;
import com.looslidias.playlistsuggestion.model.playlist.dto.PlaylistDTO;
import com.looslidias.playlistsuggestion.model.suggestion.queue.PlaylistSuggestionLatLongQueueDTO;
import com.looslidias.playlistsuggestion.model.wheater.dto.WeatherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Service
public class PlaylistSuggestionService {

    private WeatherService weatherService;
    private PlaylistService playlistService;
    private SuggestionGenreService suggestionGenreService;
    private CallbackService callbackService;
    private JmsTemplate jmsTemplate;
    private ActiveMQProperties activeMQProperties;
    private ObjectMapper objectMapper;

    @Autowired
    public PlaylistSuggestionService(WeatherService weatherService, PlaylistService playlistService, SuggestionGenreService suggestionGenreService,
                                     CallbackService callbackService, JmsTemplate jmsTemplate, ActiveMQProperties activeMQProperties, ObjectMapper objectMapper) {
        this.weatherService = weatherService;
        this.playlistService = playlistService;
        this.suggestionGenreService = suggestionGenreService;
        this.jmsTemplate = jmsTemplate;
        this.callbackService = callbackService;
        this.activeMQProperties = activeMQProperties;
        this.objectMapper = objectMapper;
    }

    public PlaylistSuggestionDTO searchPlaylistByCity(final String city, final String countryCode) {
        Preconditions.checkArgument(city != null, "City must be specified");
        Preconditions.checkArgument(countryCode != null, "CountryCode must be specified");

        WeatherDTO weatherDTO = weatherService.searchWeather(city, countryCode);
        List<String> genres = suggestionGenreService.searchGenres(weatherDTO);
        List<PlaylistDTO> playlists = genres.stream().map(genre -> playlistService.searchGenreTracks(genre)).filter(Objects::nonNull).collect(Collectors.toList());
        return PlaylistSuggestionUtils.buildPlaylistSuggestionResponse(weatherDTO, playlists);
    }

    public PlaylistSuggestionDTO searchPlaylistByLatLong(final Double lat, final Double lon) {
        Preconditions.checkArgument(lat != null, "Latitude must be specified");
        Preconditions.checkArgument(lon != null, "Longitude must be specified");

        WeatherDTO weatherDTO = weatherService.searchWeather(lat, lon);
        List<String> genres = suggestionGenreService.searchGenres(weatherDTO);
        List<PlaylistDTO> playlists = genres.stream().map(genre -> playlistService.searchGenreTracks(genre)).filter(Objects::nonNull).collect(Collectors.toList());
        return PlaylistSuggestionUtils.buildPlaylistSuggestionResponse(weatherDTO, playlists);
    }

    public PlaylistSuggestionCityQueueDTO schedulePlaylistByCity(final PlaylistSuggestionCityRequest request) throws JsonProcessingException {
        Preconditions.checkArgument(request.getCity() != null, "City must be specified");
        Preconditions.checkArgument(request.getCountryCode() != null, "Contry Code must be specified");
        Preconditions.checkArgument(request.getCallbackUrl() != null, "CallbackUrl must be specified");

        PlaylistSuggestionCityQueueDTO queueBean = buildCityQueueDto(request.getCity(), request.getCountryCode(), request.getCallbackUrl());
        jmsTemplate.convertAndSend(activeMQProperties.getPlaylistCitySuggestionQueue(), objectMapper.writeValueAsString(queueBean));
        return queueBean;
    }

    public PlaylistSuggestionLatLongQueueDTO schedulePlaylistByLatLong(final PlaylistSuggestionLatLongRequest request) throws JsonProcessingException {
        Preconditions.checkArgument(request.getLat() != null, "Latitude must be specified");
        Preconditions.checkArgument(request.getLon() != null, "Longitude must be specified");
        Preconditions.checkArgument(request.getCallbackUrl() != null, "CallbackUrl must be specified");

        PlaylistSuggestionLatLongQueueDTO queueBean = buildLatLongQueueDto(request.getLat(), request.getLon(), request.getCallbackUrl());
        jmsTemplate.convertAndSend(activeMQProperties.getPlaylistLatLongSuggestionQueue(), objectMapper.writeValueAsString(queueBean));
        return queueBean;
    }

    public void processPlaylistByCity(final PlaylistSuggestionCityQueueDTO queueDTO) {
        long startTime = System.currentTimeMillis();
        WeatherDTO weatherDTO = weatherService.searchWeather(queueDTO.getCity(), queueDTO.getCountryCode());
        List<String> genres = suggestionGenreService.searchGenres(weatherDTO);

        List<PlaylistDTO> playlists = genres.stream().map(genre -> playlistService.searchGenreTracks(genre)).filter(Objects::nonNull).collect(Collectors.toList());
        PlaylistSuggestionDTO response = PlaylistSuggestionUtils.buildPlaylistSuggestionResponse(weatherDTO, playlists);
        long endTime = System.currentTimeMillis();

        callbackService.sendCallback(queueDTO.getCallback(), CallbackUtils.buildCallbackDTO(true, endTime - startTime, response));
    }

    public void processPlaylistByLatLong(final PlaylistSuggestionLatLongQueueDTO queueDTO) {
        long startTime = System.currentTimeMillis();
        WeatherDTO weatherDTO = weatherService.searchWeather(queueDTO.getLat(), queueDTO.getLon());
        List<String> genres = suggestionGenreService.searchGenres(weatherDTO);

        List<PlaylistDTO> playlists = genres.stream().map(genre -> playlistService.searchGenreTracks(genre)).filter(Objects::nonNull).collect(Collectors.toList());
        PlaylistSuggestionDTO response = PlaylistSuggestionUtils.buildPlaylistSuggestionResponse(weatherDTO, playlists);
        long endTime = System.currentTimeMillis();

        callbackService.sendCallback(queueDTO.getCallback(), CallbackUtils.buildCallbackDTO(true, endTime - startTime, response));
    }

    private PlaylistSuggestionLatLongQueueDTO buildLatLongQueueDto(final Double lat, final Double lon, final String callback) {
        return PlaylistSuggestionLatLongQueueDTO.builder()
                .lat(lat)
                .lon(lon)
                .callback(callback)
                .build();
    }

    private PlaylistSuggestionCityQueueDTO buildCityQueueDto(final String city, final String countryCode, final String callback) {
        return PlaylistSuggestionCityQueueDTO.builder()
                .city(city)
                .countryCode(countryCode)
                .callback(callback)
                .build();
    }
}
