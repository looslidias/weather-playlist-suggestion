package com.looslidias.playlistsuggestion.core.service.playlist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.looslidias.playlistsuggestion.core.properties.queue.ActiveMQProperties;
import com.looslidias.playlistsuggestion.core.service.callback.CallbackService;
import com.looslidias.playlistsuggestion.core.service.music.MusicService;
import com.looslidias.playlistsuggestion.core.service.playlist.genre.PlaylistGenreService;
import com.looslidias.playlistsuggestion.core.service.weather.WeatherService;
import com.looslidias.playlistsuggestion.core.utils.CallbackUtils;
import com.looslidias.playlistsuggestion.core.utils.PlaylistSuggestionUtils;
import com.looslidias.playlistsuggestion.model.playlist.PlaylistCityRequest;
import com.looslidias.playlistsuggestion.model.playlist.PlaylistLatLongRequest;
import com.looslidias.playlistsuggestion.model.playlist.PlaylistSuggestionDTO;
import com.looslidias.playlistsuggestion.model.music.MusicDTO;
import com.looslidias.playlistsuggestion.model.queue.PlaylistCitySuggestionQueueDTO;
import com.looslidias.playlistsuggestion.model.queue.PlaylistLatLongSuggestionQueueDTO;
import com.looslidias.playlistsuggestion.model.wheater.WeatherDTO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Service
public class PlaylistSuggestionService {

    private WeatherService weatherService;
    private MusicService musicService;
    private PlaylistGenreService playlistGenreService;
    private CallbackService callbackService;
    private JmsTemplate jmsTemplate;
    private ActiveMQProperties activeMQProperties;
    private ObjectMapper objectMapper;

    public PlaylistSuggestionService(WeatherService weatherService, MusicService musicService, PlaylistGenreService playlistGenreService,
                                     CallbackService callbackService, JmsTemplate jmsTemplate, ActiveMQProperties activeMQProperties, ObjectMapper objectMapper) {
        this.weatherService = weatherService;
        this.musicService = musicService;
        this.playlistGenreService = playlistGenreService;
        this.jmsTemplate = jmsTemplate;
        this.callbackService = callbackService;
        this.activeMQProperties = activeMQProperties;
        this.objectMapper = objectMapper;
    }

    public PlaylistSuggestionDTO searchPlaylistByCity(final String city, final String countryCode) {
        Preconditions.checkArgument(city != null, "City must be specified");
        Preconditions.checkArgument(countryCode != null, "CountryCode must be specified");

        WeatherDTO weatherDTO = weatherService.searchWeather(city, countryCode);
        List<String> genres = playlistGenreService.searchGenres(weatherDTO);
        List<MusicDTO> playlists = genres.stream().map(genre -> musicService.searchGenreTracks(genre)).collect(Collectors.toList());
        return PlaylistSuggestionUtils.buildPlaylistSuggestionResponse(weatherDTO, playlists);
    }

    public PlaylistSuggestionDTO searchPlaylistByLatLong(final Double lat, final Double lon) {
        Preconditions.checkArgument(lat != null, "Latitude must be specified");
        Preconditions.checkArgument(lon != null, "Longitude must be specified");

        WeatherDTO weatherDTO = weatherService.searchWeather(lat, lon);
        List<String> genres = playlistGenreService.searchGenres(weatherDTO);
        List<MusicDTO> playlists = genres.stream().map(genre -> musicService.searchGenreTracks(genre)).collect(Collectors.toList());
        return PlaylistSuggestionUtils.buildPlaylistSuggestionResponse(weatherDTO, playlists);
    }

    public PlaylistCitySuggestionQueueDTO schedulePlaylistByCity(final PlaylistCityRequest request) throws JsonProcessingException {
        Preconditions.checkArgument(request.getCity() != null, "City must be specified");
        Preconditions.checkArgument(request.getCountryCode() != null, "Contry Code must be specified");
        Preconditions.checkArgument(request.getCallbackUrl() != null, "CallbackUrl must be specified");

        PlaylistCitySuggestionQueueDTO queueBean = buildCityQueueDto(request.getCity(), request.getCountryCode(), request.getCallbackUrl());
        jmsTemplate.convertAndSend(activeMQProperties.getPlaylistCitySuggestionQueue(), objectMapper.writeValueAsString(queueBean));
        return queueBean;
    }

    public PlaylistLatLongSuggestionQueueDTO schedulePlaylistByLatLong(final PlaylistLatLongRequest request) throws JsonProcessingException {
        Preconditions.checkArgument(request.getLat() != null, "Latitude must be specified");
        Preconditions.checkArgument(request.getLon() != null, "Longitude must be specified");
        Preconditions.checkArgument(request.getCallbackUrl() != null, "CallbackUrl must be specified");

        PlaylistLatLongSuggestionQueueDTO queueBean = buildLatLongQueueDto(request.getLat(), request.getLon(), request.getCallbackUrl());
        jmsTemplate.convertAndSend(activeMQProperties.getPlaylistLatLongSuggestionQueue(), objectMapper.writeValueAsString(queueBean));
        return queueBean;
    }

    public void processPlaylistByCity(final PlaylistCitySuggestionQueueDTO queueDTO) {
        long startTime = System.currentTimeMillis();
        WeatherDTO weatherDTO = weatherService.searchWeather(queueDTO.getCity(), queueDTO.getCountryCode());
        List<String> genres = playlistGenreService.searchGenres(weatherDTO);

        List<MusicDTO> playlists = genres.stream().map(genre -> musicService.searchGenreTracks(genre)).collect(Collectors.toList());
        PlaylistSuggestionDTO response = PlaylistSuggestionUtils.buildPlaylistSuggestionResponse(weatherDTO, playlists);
        long endTime = System.currentTimeMillis();

        callbackService.sendCallback(queueDTO.getCallback(), CallbackUtils.buildCallbackDTO(true, endTime - startTime, response));
    }

    public void processPlaylistByLatLong(final PlaylistLatLongSuggestionQueueDTO queueDTO) {
        long startTime = System.currentTimeMillis();
        WeatherDTO weatherDTO = weatherService.searchWeather(queueDTO.getLat(), queueDTO.getLon());
        List<String> genres = playlistGenreService.searchGenres(weatherDTO);

        List<MusicDTO> playlists = genres.stream().map(genre -> musicService.searchGenreTracks(genre)).collect(Collectors.toList());
        PlaylistSuggestionDTO response = PlaylistSuggestionUtils.buildPlaylistSuggestionResponse(weatherDTO, playlists);
        long endTime = System.currentTimeMillis();

        callbackService.sendCallback(queueDTO.getCallback(), CallbackUtils.buildCallbackDTO(true, endTime - startTime, response));
    }

    private PlaylistLatLongSuggestionQueueDTO buildLatLongQueueDto(final Double lat, final Double lon, final String callback) {
        return PlaylistLatLongSuggestionQueueDTO.builder()
                .lat(lat)
                .lon(lon)
                .callback(callback)
                .build();
    }

    private PlaylistCitySuggestionQueueDTO buildCityQueueDto(final String city, final String countryCode, final String callback) {
        return PlaylistCitySuggestionQueueDTO.builder()
                .city(city)
                .countryCode(countryCode)
                .callback(callback)
                .build();
    }
}
