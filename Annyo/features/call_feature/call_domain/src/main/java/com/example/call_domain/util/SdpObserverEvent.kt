package com.example.call_domain.util

import org.webrtc.SessionDescription

sealed interface SdpObserverEvent {
    data class OnCreateSuccess(val sessionDescription: SessionDescription?) : SdpObserverEvent
    data object OnSetSuccess : SdpObserverEvent
    data class OnCreateFailure(val error: String?) : SdpObserverEvent
    data class OnSetFailure(val error: String?) : SdpObserverEvent
}
