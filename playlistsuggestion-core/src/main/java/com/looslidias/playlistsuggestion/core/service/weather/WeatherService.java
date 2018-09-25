package com.looslidias.playlistsuggestion.core.service.weather;

import com.looslidias.playlistsuggestion.model.wheater.WeatherDTO;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public interface WeatherService {

    /**
     * Searches weather data according to given {@code city} and {@code countryCode}.
     * @param city Analysed city.
     * @param countryCode Analysed countryCode.
     * @return {@link WeatherDTO} containing track details
     */
    WeatherDTO searchWeather(final String city, final String countryCode);

    /**
     * Searches weather data according to given {@code lat} and {@code lon}.
     * @param lat Analysed latitude.
     * @param lon Analysed longitude.
     * @return {@link WeatherDTO} containing track details
     */
    WeatherDTO searchWeather(final Double lat, final Double lon);
}
