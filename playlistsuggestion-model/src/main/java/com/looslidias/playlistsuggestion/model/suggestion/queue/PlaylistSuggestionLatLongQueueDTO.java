package com.looslidias.playlistsuggestion.model.suggestion.queue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PlaylistSuggestionLatLongQueueDTO {
    private Double lat;
    private Double lon;
    private String callback;
}
