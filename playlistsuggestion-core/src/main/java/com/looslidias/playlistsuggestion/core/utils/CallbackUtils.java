package com.looslidias.playlistsuggestion.core.utils;

import com.looslidias.playlistsuggestion.model.callback.CallbackDTO;

/**
 * @author Created by Rafael Loosli Dias (rafaldias@gmail.com) on 23/09/18
 */
public final class CallbackUtils {
    private CallbackUtils() {
    }

    public static CallbackDTO buildCallbackDTO(boolean success, long processingTime, final Object data) {
        return CallbackDTO.builder()
                .success(success)
                .processingTime(processingTime)
                .data(data)
                .build();
    }

}
