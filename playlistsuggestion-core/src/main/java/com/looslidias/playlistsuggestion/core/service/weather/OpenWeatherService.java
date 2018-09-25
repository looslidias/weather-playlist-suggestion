package com.looslidias.playlistsuggestion.core.service.weather;

import com.google.common.base.Preconditions;
import com.looslidias.playlistsuggestion.core.properties.weather.WeatherProperties;
import com.looslidias.playlistsuggestion.core.utils.WeatherUtils;
import com.looslidias.playlistsuggestion.model.wheater.WeatherDTO;
import com.looslidias.playlistsuggestion.model.wheater.openweather.OpenWeatherResponseDTO;
import org.apache.commons.text.StrSubstitutor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@Service
public class OpenWeatherService implements WeatherService {

    private WeatherProperties weatherProperties;
    private RestTemplate restTemplate;

    public OpenWeatherService(WeatherProperties weatherProperties) {
        this.weatherProperties = weatherProperties;
        this.restTemplate = new RestTemplate();
    }

    @Cacheable(value = "weather-city", key = "{#city, #countryCode}")
    @Override
    public WeatherDTO searchWeather(final String city, final String countryCode) {
        Preconditions.checkArgument(city != null, "City must be specified");
        Preconditions.checkArgument(countryCode != null, "CountryCode must be specified");
        OpenWeatherResponseDTO response = restTemplate.getForObject(buildCityRequestUrl(city, countryCode), OpenWeatherResponseDTO.class);
        return WeatherUtils.buildWeatherResponse(response);
    }

    @Cacheable(value = "weather-lat-long", key = "{#lat, #lon}")
    @Override
    public WeatherDTO searchWeather(final Double lat, final Double lon) {
        Preconditions.checkArgument(lat != null, "Latitude must be specified");
        Preconditions.checkArgument(lon != null, "Longitude must be specified");
        OpenWeatherResponseDTO response = restTemplate.getForObject(buildLatLongRequestUrl(lat, lon), OpenWeatherResponseDTO.class);
        return WeatherUtils.buildWeatherResponse(response);
    }

    private String buildCityRequestUrl(final String city, final String countryCode) {
        Map<String, String> substitutes = new HashMap<>();
        substitutes.put("city", city);
        substitutes.put("countryCode", countryCode);
        substitutes.put("appId", weatherProperties.getOpenWeather().getAppKey());

        StrSubstitutor sub = new StrSubstitutor(substitutes);
        return sub.replace(weatherProperties.getOpenWeather().getCityBaseUrl());
    }

    private String buildLatLongRequestUrl(final Double lat, final Double lon) {
        Map<String, String> substitutes = new HashMap<>();
        substitutes.put("lat", String.valueOf(lat));
        substitutes.put("lon", String.valueOf(lon));
        substitutes.put("appId", weatherProperties.getOpenWeather().getAppKey());

        StrSubstitutor sub = new StrSubstitutor(substitutes);
        return sub.replace(weatherProperties.getOpenWeather().getLatLongBaseUrl());
    }
}
