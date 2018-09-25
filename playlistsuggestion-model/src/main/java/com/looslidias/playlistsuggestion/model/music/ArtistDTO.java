package com.looslidias.playlistsuggestion.model.music;

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
public class ArtistDTO implements Serializable {
    private static final long serialVersionUID = -5745699701453395688L;
    private String name;
    private String url;
}
