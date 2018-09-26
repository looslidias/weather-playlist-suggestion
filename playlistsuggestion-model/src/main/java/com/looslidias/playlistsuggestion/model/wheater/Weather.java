package com.looslidias.playlistsuggestion.model.wheater;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 25/09/18
 */
@Entity
@Table(name = "weather", indexes = {
        @Index(name = "lat_long_idx", columnList = "latitude,longitude"),
        @Index(name = "city_idx", columnList = "city,country_code")})
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String city;

    @Column(name = "country_code")
    private String countryCode;

    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column
    private Float pressure;
    @Column
    private Float humidity;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "temperature", column = @Column(name = "temperature_celsius")),
            @AttributeOverride(name = "minTemperature", column = @Column(name = "min_temperature_celsius")),
            @AttributeOverride(name = "maxTemperature", column = @Column(name = "max_temperature_celsius"))
    })
    private Temperature celcius;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "temperature", column = @Column(name = "temperature_fahrenheit")),
            @AttributeOverride(name = "minTemperature", column = @Column(name = "min_temperature_fahrenheit")),
            @AttributeOverride(name = "maxTemperature", column = @Column(name = "max_temperature_fahrenheit"))
    })
    private Temperature fahrenheit;

    @Column(name = "created_at")
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updatedAt;
}
