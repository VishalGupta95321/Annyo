package com.example.call_domain.di

import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.repository.RtcSignalingRepository
import com.example.call_domain.use_cases.rtc_use_case.CreatePeerConnectionUseCase
import com.example.call_domain.use_cases.rtc_use_case.HandleIncomingCallHangUpRequestUseCase
import com.example.call_domain.use_cases.rtc_use_case.HandleIncomingCallOfferUseCase
import com.example.call_domain.use_cases.rtc_use_case.HandleLocalIceCandidateUseCase
import com.example.call_domain.use_cases.rtc_use_case.HandlePeersAnswerUseCase
import com.example.call_domain.use_cases.rtc_use_case.HandleRemoteIceCandidateUseCase
import com.example.call_domain.use_cases.rtc_use_case.HangUpOutgoingCallUseCase
import com.example.call_domain.use_cases.rtc_use_case.ReconnectIceConnectionUseCase
import com.example.call_domain.use_cases.rtc_use_case.RegisterCallSessionUseCase
import com.example.call_domain.use_cases.rtc_use_case.RtcUseCases
import com.example.call_domain.use_cases.rtc_use_case.SendOutgoingCallOfferUseCase
import com.example.call_domain.use_cases.rtc_use_case.SetUpSurfaceViewRendererForVideoCapturingUseCase
import com.example.call_domain.use_cases.rtc_use_case.SwitchCameraUseCase
import com.example.call_domain.use_cases.rtc_use_case.SwitchFromAudioToVideoCallOfferUseCase
import com.example.call_domain.use_cases.rtc_use_case.ToggleAudioOutputUseCase
import com.example.call_domain.use_cases.rtc_use_case.ToggleMicUseCase
import com.example.call_domain.use_cases.rtc_use_case.ToggleVideoUseCase
import com.example.call_domain.use_cases.signalling_use_case.ObserveSignallingEventUseCase
import com.example.call_domain.use_cases.signalling_use_case.ObserveSignallingSocketConnectionEventUseCase
import com.example.call_domain.use_cases.signalling_use_case.SendSignallingAcknowledgmentsUseCase
import com.example.call_domain.use_cases.signalling_use_case.SendSignallingEventUseCase
import com.example.call_domain.use_cases.signalling_use_case.SignallingUseCases
import com.example.call_domain.util.RtcMediaCaptureClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CallDomainModule {

    @Provides
    @Singleton
    fun provideRtcUseCase(
        rtcConnectionRepository: RtcConnectionRepository,
        rtcMediaCaptureClient: RtcMediaCaptureClient,
        signallingUseCases: SignallingUseCases
    ): RtcUseCases {
        return RtcUseCases(
            sendOutgoingCallOffer = SendOutgoingCallOfferUseCase(rtcConnectionRepository,rtcMediaCaptureClient,signallingUseCases),
            hangUpOutgoingCall = HangUpOutgoingCallUseCase(rtcConnectionRepository,rtcMediaCaptureClient,signallingUseCases),
            handleIncomingCallOffer = HandleIncomingCallOfferUseCase(rtcConnectionRepository,rtcMediaCaptureClient,signallingUseCases),
            createPeerConnection = CreatePeerConnectionUseCase(rtcConnectionRepository),
            toggleMic = ToggleMicUseCase(rtcMediaCaptureClient),
            toggleVideo = ToggleVideoUseCase(rtcMediaCaptureClient),
            toggleAudioOutput = ToggleAudioOutputUseCase(rtcMediaCaptureClient),
            handleLocalIceCandidate = HandleLocalIceCandidateUseCase(rtcConnectionRepository,signallingUseCases),
            setUpSurfaceViewRendererForVideoCapturing = SetUpSurfaceViewRendererForVideoCapturingUseCase(rtcMediaCaptureClient),
            switchFromAudioToVideoCallOffer = SwitchFromAudioToVideoCallOfferUseCase(),
            handleIncomingCallHangUpRequest = HandleIncomingCallHangUpRequestUseCase(rtcConnectionRepository,rtcMediaCaptureClient),
            handleRemoteIceCandidate = HandleRemoteIceCandidateUseCase(rtcConnectionRepository),
            handleIncomingAnswer = HandlePeersAnswerUseCase(rtcConnectionRepository),
            reconnectIceConnection = ReconnectIceConnectionUseCase(),
            switchCamera = SwitchCameraUseCase(rtcMediaCaptureClient),
            createCallSession = RegisterCallSessionUseCase(signallingUseCases)
        )
    }

    @Provides
    @Singleton
    fun provideSignallingUseCase(
        rtcSignalingRepository: RtcSignalingRepository
    ): SignallingUseCases {
        return SignallingUseCases(
            observeSignallingEvent = ObserveSignallingEventUseCase(rtcSignalingRepository),
            observeSignallingSocketConnectionEvent = ObserveSignallingSocketConnectionEventUseCase(rtcSignalingRepository),
            sendSignallingEvent = SendSignallingEventUseCase(rtcSignalingRepository),
            sendSignallingAcknowledgmentsUseCase = SendSignallingAcknowledgmentsUseCase(rtcSignalingRepository)
        )
    }
}