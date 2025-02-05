package com.example.call_domain.util

import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.RtpReceiver

sealed interface PeerConnectionObserverEvent {
    data class OnSignalingChange(val signalingState: PeerConnection.SignalingState?)
        : PeerConnectionObserverEvent
    data class OnIceConnectionChange(val iceConnectionState: PeerConnection.IceConnectionState?)
        : PeerConnectionObserverEvent
    data class OnIceConnectionReceivingChange(val receiving: Boolean)
        : PeerConnectionObserverEvent
    data class OnIceGatheringChange(val iceGatheringState: PeerConnection.IceGatheringState?)
        : PeerConnectionObserverEvent
    data class OnIceCandidate(val iceCandidate: IceCandidate?) : PeerConnectionObserverEvent
    data class OnIceCandidatesRemoved(val removedCandidates: List<IceCandidate>?)
        : PeerConnectionObserverEvent
    data class OnConnectionChange(val newState: PeerConnection.PeerConnectionState?)
        : PeerConnectionObserverEvent
    data class OnAddTrack(val receiver: RtpReceiver?, val mediaStreams: List<MediaStream>?)
        : PeerConnectionObserverEvent
    data class OnRemoveTrack(val receiver: RtpReceiver?) : PeerConnectionObserverEvent
    data class OnAddStream(val mediaStream: MediaStream?) : PeerConnectionObserverEvent
    data class OnRemoveStream(val mediaStream: MediaStream?) : PeerConnectionObserverEvent
    data class OnDataChannel(val dataChannel: DataChannel?) : PeerConnectionObserverEvent
    object OnRenegotiationNeeded : PeerConnectionObserverEvent
    object PeerConnectionFailed : PeerConnectionObserverEvent
}
