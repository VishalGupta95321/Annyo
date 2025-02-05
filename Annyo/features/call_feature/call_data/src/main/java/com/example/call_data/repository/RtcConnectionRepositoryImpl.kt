package com.example.call_data.repository

import com.annyo.network.di.DefaultDispatcher
import com.annyo.network.di.IoDispatcher
import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.util.OfferType
import com.example.call_domain.util.PeerConnectionObserverEvent
import com.example.call_domain.util.SdpObserverEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.webrtc.DataChannel
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.MediaStreamTrack
import org.webrtc.PeerConnection
import org.webrtc.PeerConnection.Observer
import org.webrtc.PeerConnectionFactory
import org.webrtc.RtpReceiver
import org.webrtc.SdpObserver
import org.webrtc.SessionDescription
import javax.inject.Inject
import kotlin.coroutines.resume

class RtcConnectionRepositoryImpl @Inject constructor(
    private val rtcPeerConnectionFactory: PeerConnectionFactory,
    private val iceServers: PeerConnection.IceServer,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher
    private val defaultDispatcher: CoroutineDispatcher,
) : RtcConnectionRepository {

    private var peerConnection: PeerConnection? = null

    override fun createPeerConnection(
        //  onConnectionFailed : (PeerConnectionObserverEvent)-> Unit
    ) = callbackFlow {
        peerConnection = rtcPeerConnectionFactory.createPeerConnection(
            listOf(iceServers),
            object : Observer {
                override fun onSignalingChange(p0: PeerConnection.SignalingState?) {
                    trySend(PeerConnectionObserverEvent.OnSignalingChange(p0))
                }

                override fun onIceConnectionChange(p0: PeerConnection.IceConnectionState?) {
                    trySend(PeerConnectionObserverEvent.OnIceConnectionChange(p0))
                }

                override fun onIceConnectionReceivingChange(p0: Boolean) {
                    trySend(PeerConnectionObserverEvent.OnIceConnectionReceivingChange(p0))
                }

                override fun onIceGatheringChange(p0: PeerConnection.IceGatheringState?) {
                    trySend(PeerConnectionObserverEvent.OnIceGatheringChange(p0))
                }

                override fun onIceCandidate(p0: IceCandidate?) {
                    trySend(PeerConnectionObserverEvent.OnIceCandidate(p0))
                }

                override fun onIceCandidatesRemoved(p0: Array<out IceCandidate>?) {
                    trySend(PeerConnectionObserverEvent.OnIceCandidatesRemoved(p0?.toList()))
                }

                override fun onAddStream(p0: MediaStream?) {
                    trySend(PeerConnectionObserverEvent.OnAddStream(p0))
                }

                override fun onRemoveStream(p0: MediaStream?) {
                    trySend(PeerConnectionObserverEvent.OnRemoveStream(p0))
                }

                override fun onAddTrack(
                    receiver: RtpReceiver?,
                    mediaStreams: Array<out MediaStream>?
                ) {
                    trySend(
                        PeerConnectionObserverEvent.OnAddTrack(
                            receiver,
                            mediaStreams?.toList()
                        )
                    )
                }

                override fun onRemoveTrack(receiver: RtpReceiver?) {
                    trySend(PeerConnectionObserverEvent.OnRemoveTrack(receiver))
                }

                override fun onDataChannel(p0: DataChannel?) {
                    trySend(PeerConnectionObserverEvent.OnDataChannel(p0))
                }

                override fun onRenegotiationNeeded() {
                    trySend(PeerConnectionObserverEvent.OnRenegotiationNeeded)
                }
            }
        )
        // peerConnection?:onConnectionFailed(PeerConnectionObserverEvent.PeerConnectionFailed)
        peerConnection ?: (PeerConnectionObserverEvent.PeerConnectionFailed)

        awaitClose()
    }.flowOn(ioDispatcher)


    override suspend fun closePeerConnection() {
        withContext(defaultDispatcher) {
            peerConnection?.close()
        }
    }

    override suspend fun addIceCandidate(
        iceCandidate: IceCandidate
    ) {
        withContext(defaultDispatcher) {
            peerConnection?.addIceCandidate(iceCandidate)
        }
    }

    override suspend fun createOffer(
        offerType: OfferType,
    ): SdpObserverEvent = withContext(defaultDispatcher) {
        suspendCancellableCoroutine { continuation ->
            peerConnection?.createOffer(
                object : SdpObserver {
                    override fun onCreateSuccess(p0: SessionDescription?) {
                        continuation.resume(SdpObserverEvent.OnCreateSuccess(p0))
                    }

                    override fun onSetSuccess() {
                        continuation.resume(SdpObserverEvent.OnSetSuccess)
                    }

                    override fun onCreateFailure(p0: String?) {
                        continuation.resume(SdpObserverEvent.OnCreateFailure(p0))
                    }

                    override fun onSetFailure(p0: String?) {
                        continuation.resume(SdpObserverEvent.OnSetFailure(p0))
                        //continuation.resumeWithException()  // Have option to use this function too
                    }

                },
                when (offerType) {
                    OfferType.AudioCall -> OfferType.AudioCall.mediaConstraints
                    OfferType.VideoCall -> OfferType.VideoCall.mediaConstraints
                    OfferType.Reconnection -> OfferType.Reconnection.mediaConstraints
                }
            )
            continuation.invokeOnCancellation { }
        }
    }

    override suspend fun createAnswer(
        receivedOfferType: OfferType,
    ): SdpObserverEvent = withContext(defaultDispatcher) {
        suspendCancellableCoroutine { continuation ->
            peerConnection?.createAnswer(
                object : SdpObserver {
                    override fun onCreateSuccess(p0: SessionDescription?) {
                        continuation.resume(SdpObserverEvent.OnCreateSuccess(p0))
                    }

                    override fun onSetSuccess() {
                        continuation.resume(SdpObserverEvent.OnSetSuccess)
                    }

                    override fun onCreateFailure(p0: String?) {
                        continuation.resume(SdpObserverEvent.OnCreateFailure(p0))
                    }

                    override fun onSetFailure(p0: String?) {
                        continuation.resume(SdpObserverEvent.OnCreateFailure(p0))
                    }

                }, when (receivedOfferType) {
                    OfferType.AudioCall -> OfferType.AudioCall.mediaConstraints
                    OfferType.VideoCall -> OfferType.VideoCall.mediaConstraints
                    OfferType.Reconnection -> OfferType.Reconnection.mediaConstraints
                }
            )
            continuation.invokeOnCancellation { }
        }
    }

    override suspend fun addTrack(
        mediaStream: MediaStreamTrack
    ) {
        withContext(defaultDispatcher) {
            peerConnection?.addTrack(
                mediaStream
            )
        }
    }

    override fun restartIce() {
        TODO("Later after implementing the base functionality")
    }

    override suspend fun setLocalDescription(
        localDescription: SessionDescription
    ): SdpObserverEvent = withContext(defaultDispatcher) {
        suspendCancellableCoroutine { continuation ->
            peerConnection?.setLocalDescription(
                object : SdpObserver {
                    override fun onCreateSuccess(p0: SessionDescription?) {
                        continuation.resume(SdpObserverEvent.OnCreateSuccess(p0))
                    }

                    override fun onSetSuccess() {
                        continuation.resume(SdpObserverEvent.OnSetSuccess)
                    }

                    override fun onCreateFailure(p0: String?) {
                        continuation.resume(SdpObserverEvent.OnCreateFailure(p0))
                    }

                    override fun onSetFailure(p0: String?) {
                        continuation.resume(SdpObserverEvent.OnSetFailure(p0))
                    }
                },
                localDescription
            )
            continuation.invokeOnCancellation { }
        }
    }

    override suspend fun setRemoteDescription(
        remoteDescription: SessionDescription
    ) = withContext(defaultDispatcher) {
        suspendCancellableCoroutine { continuation ->
            peerConnection?.setRemoteDescription(
                object : SdpObserver {
                    override fun onCreateSuccess(p0: SessionDescription?) {
                        continuation.resume(SdpObserverEvent.OnCreateSuccess(p0))
                    }

                    override fun onSetSuccess() {
                        continuation.resume(SdpObserverEvent.OnSetSuccess)
                    }

                    override fun onCreateFailure(p0: String?) {
                        continuation.resume(SdpObserverEvent.OnCreateFailure(p0))
                    }

                    override fun onSetFailure(p0: String?) {
                        continuation.resume(SdpObserverEvent.OnSetFailure(p0))
                    }
                },
                remoteDescription
            )
            continuation.invokeOnCancellation { }
        }
    }
}


