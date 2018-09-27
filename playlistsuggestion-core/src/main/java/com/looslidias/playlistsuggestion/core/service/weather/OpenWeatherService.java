package com.looslidias.playlistsuggestion.core.service.weather;

import com.google.common.base.Preconditions;
import com.looslidias.playlistsuggestion.core.properties.weather.WeatherProperties;
import com.looslidias.playlistsuggestion.core.utils.WeatherUtils;
import com.looslidias.playlistsuggestion.model.wheater.Weather;
import com.looslidias.playlistsuggestion.model.wheater.dao.WeatherRepository;
import com.looslidias.playlistsuggestion.model.wheater.dto.WeatherDTO;
import com.looslidias.playlistsuggestion.model.wheater.dto.openweather.OpenWeatherResponseDTO;
import org.apache.commons.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
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
    private WeatherRepository weatherRepository;
    private RestTemplate restTemplate;

    @Autowired
    public OpenWeatherService(WeatherProperties weatherProperties, WeatherRepository weatherRepository) {
        this.weatherProperties = weatherProperties;
        this.weatherRepository = weatherRepository;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Firstly, tries to retrieve information from cache (Redis);
     * Secondly, if there is no cache hot, tries to reach Vendor (OpenWeatherMap);
     * Finally, if Vendor does not response as expected, tries to gather data from database.
     * @param city Analysed city
     * @param countryCode Analysed Country Code
     * @return {@link WeatherDTO}
     */
    @Cacheable(value = "weather-city", key = "{#city, #countryCode}", unless="#result == null")
    @Override
    public WeatherDTO searchWeather(final String city, final String countryCode) {
        Preconditions.checkArgument(city != null, "City must be specified");
        Preconditions.checkArgument(countryCode != null, "CountryCode must be specified");

        String url = buildCityRequestUrl(city, countryCode);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<OpenWeatherResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(headers), OpenWeatherResponseDTO.class);

        Weather weather;
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            weather = weatherRepository.save(WeatherUtils.buildWeather(response.getBody()));
        } else {
            weather = weatherRepository.findByCityAndCountryCode(city.toUpperCase(), countryCode.toUpperCase());
        }
        return WeatherUtils.weatherFromModel(weather);
    }

    /**
     * Firstly, tries to retrieve information from cache (Redis);
     * Secondly, if there is no cache hot, tries to reach Vendor (OpenWeatherMap);
     * Finally, if Vendor does not response as expected, tries to gather data from database.
     * @param lat Analysed latitude
     * @param lon Analysed longitude
     * @return {@link WeatherDTO}
     */
    @Cacheable(value = "weather-lat-long", key = "{#lat, #lon}", unless="#result == null")
    @Override
    public WeatherDTO searchWeather(final Double lat, final Double lon) {
        Preconditions.checkArgument(lat != null, "Latitude must be specified");
        Preconditions.checkArgument(lon != null, "Longitude must be specified");

        String url = buildLatLongRequestUrl(lat, lon);
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<OpenWeatherResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(headers), OpenWeatherResponseDTO.class);

        Weather weather = weatherRepository.save(WeatherUtils.buildWeather(response.getBody()));
        // TODO search by lat long, if no response was retrieved (postgis)
        return WeatherUtils.weatherFromModel(weather);
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
