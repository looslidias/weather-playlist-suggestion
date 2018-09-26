package com.looslidias.playlistsuggestion.model.wheater;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 25/09/18
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Temperature {
    @Column
    private Float temperature;
    @Column(name = "min_temperature")
    private Float minTemperature;
    @Column(name = "max_temperature")
    private Float maxTemperature;
}
