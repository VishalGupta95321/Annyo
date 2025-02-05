//package com.example.call_data.repository
//
//import com.annyo.firebase_service.domain.use_case.SignedUserUseCases
//import com.example.call_domain.util.PeerConnectionObserverEvent
//import com.example.call_domain.repository.CallRepository
//import com.example.call_domain.repository.RtcConnectionRepository
//import com.example.call_domain.repository.RtcSignalingRepository
//import com.example.call_domain.util.CallType
//import com.example.utils.GetBackBasic
//import kotlinx.coroutines.flow.flow
//import org.webrtc.MediaStream
//import javax.inject.Inject
//
//class CallRepositoryImpl @Inject constructor(
//    private val rtcConnectionRepository: RtcConnectionRepository,
//    private val rtcSignalingRepository: RtcSignalingRepository,
//    private val signedUserUseCases: SignedUserUseCases,
//) : CallRepository {
//
//    private val userId = signedUserUseCases.getUid()
//    private var _callType: CallType? = null
//
//    override suspend fun beginCall(
//        callType: CallType,
//        toId: String,
//        localMediaStream: MediaStream, // contains both audio and video tracks
//        onConnectionFailed: () -> Unit  /// add the reason
//    ) = flow {
//        if (!userId.isNullOrBlank()) {
//            _callType = callType
//            rtcConnectionRepository.createPeerConnection()
//                .collect { peerConnectionObserverEvent ->
//                    when (peerConnectionObserverEvent) {
//                        is PeerConnectionObserverEvent.OnAddStream -> {}
//                        is PeerConnectionObserverEvent.OnAddTrack -> {
//                            emit(peerConnectionObserverEvent.receiver?.track())
//                        }
//
//                        is PeerConnectionObserverEvent.OnConnectionChange -> {
//
//                        }
//
//                        is PeerConnectionObserverEvent.OnDataChannel -> {}
//                        is PeerConnectionObserverEvent.OnIceCandidate -> {
//                            peerConnectionObserverEvent.iceCandidate?.let {
//                                rtcConnectionRepository.addIceCandidate(it)
//                                rtcSignalingRepository.sendSignallingEvent(
//                                    SignalingEventReqRes.OnIceCandidate(
//                                        candidate = it,
//                                        fromUserId = userId,
//                                        toUserId = toId
//                                    )
//                                )
//                            }
//                        }
//
//                        is PeerConnectionObserverEvent.OnIceCandidatesRemoved -> {}
//                        is PeerConnectionObserverEvent.OnIceConnectionChange -> {}
//                        is PeerConnectionObserverEvent.OnIceConnectionReceivingChange -> {
//                            // implement ice restart
//                        }
//
//                        is PeerConnectionObserverEvent.OnIceGatheringChange -> {}
//                        is PeerConnectionObserverEvent.OnRemoveStream -> {}
//                        is PeerConnectionObserverEvent.OnRemoveTrack -> {}
//                        PeerConnectionObserverEvent.OnRenegotiationNeeded -> {}
//                        is PeerConnectionObserverEvent.OnSignalingChange -> {}
//                        PeerConnectionObserverEvent.PeerConnectionFailed -> {
//                            onConnectionFailed()
//                        }
//                    }
//                }
//        } else onConnectionFailed()
//    }
//
////    override fun beginVideoCall():GetBackBasic {
////        TODO("Not yet implemented")
////    }
//
//    override suspend fun hangUpCall(
//        toId: String,
//    ) {
//        rtcConnectionRepository.closePeerConnection()
//        if (!userId.isNullOrBlank())
//            rtcSignalingRepository.sendSignallingEvent(
//                SignalingEventReqRes.OnHangUp(
//                    fromUserId = userId,
//                    toUserId = toId
//                )
//            )
//    }
//
//    override suspend fun toggleAudioVideo(
//        localMediaStream: MediaStream,
//    ): GetBackBasic {
//        when (_callType) {
//            CallType.AudioCall -> {
//
//            }
//
//            CallType.VideoCall -> {
//
//            }
//
//            null -> TODO()
//        }
//    }
//
//    override fun switchToSpeaker(): GetBackBasic {
//        TODO("Not yet implemented")
//    }
//}