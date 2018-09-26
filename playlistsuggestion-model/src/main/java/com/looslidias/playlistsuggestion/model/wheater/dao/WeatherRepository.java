package com.looslidias.playlistsuggestion.model.wheater.dao;

import com.looslidias.playlistsuggestion.model.wheater.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 25/09/18
 */
@Repository
public interface WeatherRepository extends JpaRepository<Weather, String> {
    Weather findByCityAndCountryCode(String city, String countryCode);
}
