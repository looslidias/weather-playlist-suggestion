package com.looslidias.playlistsuggestion.model.music;

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
public class MusicDTO implements Serializable {
    private static final long serialVersionUID = 8579730682205501624L;

    private String genre;
    private List<TrackDTO> tracks;
}
