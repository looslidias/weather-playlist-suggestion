package com.looslidias.playlistsuggestion.model.wheater.dto;

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
public class TemperatureDTO implements Serializable {
    private static final long serialVersionUID = 5307099975770136758L;
    private Float temperature;
    private Float minTemperature;
    private Float maxTemperature;
}
