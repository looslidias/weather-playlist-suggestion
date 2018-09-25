package com.looslidias.playlistsuggestion.core.utils;

import com.looslidias.playlistsuggestion.model.wheater.TemperatureDTO;
import com.looslidias.playlistsuggestion.model.wheater.WeatherDTO;
import com.looslidias.playlistsuggestion.model.wheater.openweather.OpenWeatherResponseDTO;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class WeatherUtils {
    private WeatherUtils() {
    }

    public static WeatherDTO buildWeatherResponse(final OpenWeatherResponseDTO response) {
        return WeatherDTO.builder()
                .pressure(response.getTemperature().getPressure())
                .humidity(response.getTemperature().getHumidity())
                .fahrenheit(buildFahrenheitResponse(response))
                .celcius(buildCelciusResponse(response))
                .build();
    }

    private static TemperatureDTO buildCelciusResponse(final OpenWeatherResponseDTO response) {
        return TemperatureDTO.builder()
                .temperature(fahrenheitToCelsius(response.getTemperature().getTemperature()))
                .minTemperature(fahrenheitToCelsius(response.getTemperature().getMinTemperature()))
                .maxTemperature(fahrenheitToCelsius(response.getTemperature().getMaxTemperature()))
                .build();
    }

    private static TemperatureDTO buildFahrenheitResponse(OpenWeatherResponseDTO response) {
        return TemperatureDTO.builder()
                .temperature(response.getTemperature().getTemperature())
                .minTemperature(response.getTemperature().getMinTemperature())
                .maxTemperature(response.getTemperature().getMaxTemperature())
                .build();
    }

    private static float fahrenheitToCelsius(float fahrenheit) {
        return ((fahrenheit - 32) * 5) / 9;
    }
}
