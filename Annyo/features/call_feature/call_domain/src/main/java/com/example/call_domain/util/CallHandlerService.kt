package com.example.call_domain.util

import org.webrtc.IceCandidate
import org.webrtc.SessionDescription
import org.webrtc.SurfaceViewRenderer

interface CallHandlerService {
  //  suspend fun observePeerConnectionEvents(): SharedFlow<PeerConnectionObserverEvent>
//    suspend fun observeSignallingEvents(): SharedFlow<SignallingEvent>
//    suspend fun observeSignallingSocketEvents(): SharedFlow<SignallingSocketEvent>
    suspend fun sendOutgoingCallOffer(offerType:OfferType)
    suspend fun handleIncomingAnswer(sessionDescription: SessionDescription)
    suspend fun handleLocalIceCandidate(localIceCandidate: IceCandidate)
    suspend fun handleIncomingCallOffer(
      receivedOfferType: OfferType,
      sessionDescription: SessionDescription
    )
    suspend fun hangUpOutgoingCall()
    suspend fun switchCamera()
    suspend fun handleRemoteIceCandidate(remoteIceCandidate: IceCandidate)
    fun setUpSurfaceViewRendererForVideoCapturing(localSurfaceRenderer: SurfaceViewRenderer)
    suspend fun handleIncomingCallHangUpRequest()
    fun switchFromAudioToVideoCallOffer()
    fun reconnectIceConnection()
    fun toggleAudioOutput()
    fun toggleVideo()
    fun toggleMic()
}