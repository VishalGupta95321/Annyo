package com.example.call_domain.use_cases.rtc_use_case

data class RtcUseCases(
    val sendOutgoingCallOffer: SendOutgoingCallOfferUseCase,
    val hangUpOutgoingCall: HangUpOutgoingCallUseCase,
    val handleIncomingCallHangUpRequest: HandleIncomingCallHangUpRequestUseCase,
    val handleIncomingCallOffer: HandleIncomingCallOfferUseCase,
    val handleIncomingAnswer: HandlePeersAnswerUseCase,
    val switchFromAudioToVideoCallOffer: SwitchFromAudioToVideoCallOfferUseCase,
    val createPeerConnection: CreatePeerConnectionUseCase,
    val toggleMic: ToggleMicUseCase,
    val toggleVideo: ToggleVideoUseCase,
    val handleLocalIceCandidate: HandleLocalIceCandidateUseCase,
    val handleRemoteIceCandidate: HandleRemoteIceCandidateUseCase,
    val toggleAudioOutput: ToggleAudioOutputUseCase,
    val setUpSurfaceViewRendererForVideoCapturing: SetUpSurfaceViewRendererForVideoCapturingUseCase,
    val reconnectIceConnection: ReconnectIceConnectionUseCase,
    val switchCamera: SwitchCameraUseCase,
    val createCallSession: RegisterCallSessionUseCase
)
