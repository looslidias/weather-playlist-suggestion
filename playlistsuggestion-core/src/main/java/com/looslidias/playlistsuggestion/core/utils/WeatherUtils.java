package com.looslidias.playlistsuggestion.core.utils;

import com.looslidias.playlistsuggestion.model.wheater.Temperature;
import com.looslidias.playlistsuggestion.model.wheater.Weather;
import com.looslidias.playlistsuggestion.model.wheater.dto.TemperatureDTO;
import com.looslidias.playlistsuggestion.model.wheater.dto.WeatherDTO;
import com.looslidias.playlistsuggestion.model.wheater.dto.openweather.OpenWeatherResponseDTO;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class WeatherUtils {
    private WeatherUtils() {
    }

    public static Weather buildWeather(final OpenWeatherResponseDTO response) {
        return Weather.builder()
                .city(response.getCityName().toUpperCase())
                .countryCode(response.getSys().getCountry().toUpperCase())
                .latitude(response.getCoord().getLat())
                .longitude(response.getCoord().getLon())
                .pressure(response.getTemperature().getPressure())
                .humidity(response.getTemperature().getHumidity())
                .fahrenheit(buildFahrenheitTemperature(response))
                .celcius(buildCelciusTemperature(response))
                .build();
    }

    private static Temperature buildCelciusTemperature(final OpenWeatherResponseDTO response) {
        return Temperature.builder()
                .temperature(fahrenheitToCelsius(response.getTemperature().getTemperature()))
                .minTemperature(fahrenheitToCelsius(response.getTemperature().getMinTemperature()))
                .maxTemperature(fahrenheitToCelsius(response.getTemperature().getMaxTemperature()))
                .build();
    }

    private static Temperature buildFahrenheitTemperature(final OpenWeatherResponseDTO response) {
        return Temperature.builder()
                .temperature(response.getTemperature().getTemperature())
                .minTemperature(response.getTemperature().getMinTemperature())
                .maxTemperature(response.getTemperature().getMaxTemperature())
                .build();
    }

    public static WeatherDTO weatherFromModel(final Weather weather) {
        return WeatherDTO.builder()
                .city(weather.getCity())
                .countryCode(weather.getCountryCode())
                .pressure(weather.getPressure())
                .humidity(weather.getHumidity())
                .fahrenheit(temperatureFromModel(weather.getFahrenheit()))
                .celcius(temperatureFromModel(weather.getCelcius()))
                .updatedAt(weather.getUpdatedAt())
                .build();
    }

    private static TemperatureDTO temperatureFromModel(Temperature temperature) {
        return TemperatureDTO.builder()
                .temperature(temperature.getTemperature())
                .minTemperature(temperature.getMinTemperature())
                .maxTemperature(temperature.getMaxTemperature())
                .build();
    }

    private static float fahrenheitToCelsius(float fahrenheit) {
        return ((fahrenheit - 32) * 5) / 9;
    }
}
