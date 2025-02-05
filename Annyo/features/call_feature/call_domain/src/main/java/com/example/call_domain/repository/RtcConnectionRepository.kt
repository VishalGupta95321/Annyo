package com.example.call_domain.repository

import com.example.call_domain.util.OfferType
import com.example.call_domain.util.PeerConnectionObserverEvent
import com.example.call_domain.util.SdpObserverEvent
import kotlinx.coroutines.flow.Flow
import org.webrtc.IceCandidate
import org.webrtc.MediaStreamTrack
import org.webrtc.SessionDescription

interface RtcConnectionRepository {

    fun createPeerConnection(
        //onConnectionFailed : (PeerConnectionObserverEvent)-> Unit
    ): Flow<PeerConnectionObserverEvent>

    suspend fun closePeerConnection(
    )   // close the connection

    suspend fun addIceCandidate(
        iceCandidate: IceCandidate
    )      // get ICE candidate from signaling and add in peerConnection

    suspend fun createOffer(
        offerType: OfferType,
    ): SdpObserverEvent         // create offer and  send it through the signaling server

    suspend fun createAnswer(
        receivedOfferType: OfferType,
    ):SdpObserverEvent     //  create answer and send it through the signaling server

    suspend fun setLocalDescription(
        localDescription: SessionDescription
    ):SdpObserverEvent

    suspend fun setRemoteDescription(
        remoteDescription: SessionDescription
    ):SdpObserverEvent

    suspend fun addTrack(
        mediaStream: MediaStreamTrack
    )           //

    fun restartIce()        // do it later after the base functionality

}