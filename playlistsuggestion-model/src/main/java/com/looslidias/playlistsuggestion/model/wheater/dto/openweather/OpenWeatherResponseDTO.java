package com.looslidias.playlistsuggestion.model.wheater.dto.openweather;

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
public class OpenWeatherResponseDTO {
    @JsonProperty("name")
    private String cityName;
    private OpenWeatherCoordDTO coord;
    @JsonProperty("main")
    private OpenWeatherTemperatureDTO temperature;
    private OpenWeatherSysDataDTO sys;
}
