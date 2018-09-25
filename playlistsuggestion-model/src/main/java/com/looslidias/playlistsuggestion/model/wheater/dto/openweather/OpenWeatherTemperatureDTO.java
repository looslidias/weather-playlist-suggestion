package com.looslidias.playlistsuggestion.model.wheater.openweather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OpenWeatherTemperatureDTO {
    @JsonProperty("temp")
    private Float temperature;
    private Float pressure;
    private Float humidity;
    @JsonProperty("temp_min")
    private Float minTemperature;
    @JsonProperty("temp_max")
    private Float maxTemperature;
}
