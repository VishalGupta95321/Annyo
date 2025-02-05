package com.example.call_domain.util

import org.webrtc.MediaConstraints

sealed class OfferType(val mediaConstraints: MediaConstraints){

    data object AudioCall : OfferType(
        mediaConstraints = MediaConstraints().apply {
            mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveVideo", "false"))
            mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"))
        }
    )
    data object VideoCall : OfferType(
        mediaConstraints = MediaConstraints().apply {
            mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"))
            mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"))
        }
    )
    data object Reconnection : OfferType(
        mediaConstraints = MediaConstraints().apply {
            mandatory.add(MediaConstraints.KeyValuePair("IceRestart", "true"))
        }
    )
}