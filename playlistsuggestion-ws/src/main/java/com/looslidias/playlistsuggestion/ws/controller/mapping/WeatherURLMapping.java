package com.looslidias.playlistsuggestion.ws.controller.mapping;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class WeatherURLMapping {

    private WeatherURLMapping(){}

    private static final String VERSION = "/1.0";
    private static final String ENDPOINT = "/weather" + VERSION;
    public static final String CITY_ENDPOINT = ENDPOINT + "/city/{city}/country-code/{countryCode}";
    public static final String LAT_LONG_ENDPOINT = ENDPOINT + "/lat/{lat}/long/{long}";
}
