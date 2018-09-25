package com.looslidias.playlistsuggestion.ws.controller.mapping;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class PlaylistSuggestionMapping {

    private PlaylistSuggestionMapping(){}

    private static final String VERSION = "/1.0";
    private static final String ENDPOINT = "/playlist-suggestion" + VERSION;
    public static final String PLAYLIST_CITY_ENDPOINT = ENDPOINT + "/city/{city}/country-code/{countryCode}";
    public static final String PLAYLIST_LAT_LONG_ENDPOINT = ENDPOINT + "/lat/{lat}/long/{long}";
    public static final String PLAYLIST_CITY_ENDPOINT_ASYNC = ENDPOINT + "/async/city";
    public static final String PLAYLIST_LAT_LONG_ENDPOINT_ASYNC = ENDPOINT + "/async/lat-long";
}
