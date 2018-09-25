package com.looslidias.playlistsuggestion.model.wheater;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WeatherDTO implements Serializable {
    private static final long serialVersionUID = 8645831921694078129L;
    private Float pressure;
    private Float humidity;
    private TemperatureDTO celcius;
    private TemperatureDTO fahrenheit;
}
