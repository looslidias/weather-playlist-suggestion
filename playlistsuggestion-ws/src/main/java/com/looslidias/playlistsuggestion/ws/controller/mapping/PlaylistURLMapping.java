package com.looslidias.playlistsuggestion.ws.controller.mapping;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class MusicURLMapping {

    private MusicURLMapping(){}

    private static final String VERSION = "/1.0";
    private static final String ENDPOINT = "/suggestion" + VERSION;
    public static final String GENRE_TRACKS_ENDPOINT = ENDPOINT + "/genre/{genre}";
}
