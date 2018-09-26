package com.looslidias.playlistsuggestion.model.playlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TrackDTO implements Serializable {
    private static final long serialVersionUID = 6441269479869155091L;
    private String name;
    private String url;
    private Float popularity;
    private List<String> artists;
}
