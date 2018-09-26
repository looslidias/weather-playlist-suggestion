package com.looslidias.playlistsuggestion.ws.controller.mapping;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class PlaylistURLMapping {

    private PlaylistURLMapping(){}

    private static final String VERSION = "/1.0";
    private static final String ENDPOINT = "/playlist" + VERSION;
    public static final String TRACKS_GENRE_ENDPOINT = ENDPOINT + "/genre/{genre}";
}
