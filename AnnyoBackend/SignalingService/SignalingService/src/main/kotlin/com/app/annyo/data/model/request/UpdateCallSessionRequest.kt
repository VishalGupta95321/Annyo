package com.app.annyo.data.model.request

import com.app.annyo.data.entity.SignallingEvents
import java.util.*

sealed interface UpdateCallSessionRequest {
    data class UpdateIsHangUpRequested(val isHangUpRequested: Boolean) : UpdateCallSessionRequest
    data class UpdateIsConnectionEstablished(val isConnectionEstablished: Boolean) : UpdateCallSessionRequest
    data class UpdateIsCallPickedUp(val isCallPickedUp: Boolean) : UpdateCallSessionRequest
    data class UpdateIsAudioToVideoSwitchRequested(val isAudioToVideoSwitchRequested: Boolean) :
        UpdateCallSessionRequest
    data class UpdateDisconnectedAt(val date: Date?):UpdateCallSessionRequest
}