package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.model.SignallingRequests
import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.use_cases.signalling_use_case.SignallingUseCases
import com.example.call_domain.util.OfferType
import com.example.call_domain.util.RtcMediaCaptureClient
import com.example.call_domain.util.SdpObserverEvent
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import org.webrtc.SessionDescription
import javax.inject.Inject

class HandleIncomingCallOfferUseCase @Inject constructor(
    private val rtcConnectionRepository: RtcConnectionRepository,
    private val rtcMediaCaptureClient: RtcMediaCaptureClient,
    private val signallingUseCase: SignallingUseCases,
) {
    suspend operator fun invoke(
        receivedOfferType: OfferType,
        sessionDescription: SessionDescription,
    ):GetBackBasic {
        val sdpEvent = rtcConnectionRepository.setRemoteDescription(sessionDescription)
        if (sdpEvent !is SdpObserverEvent.OnCreateSuccess) return GetBack.Error()

        val answer = rtcConnectionRepository.createAnswer(receivedOfferType)
            .let { sdpObserverEvent->
                if (sdpObserverEvent !is SdpObserverEvent.OnCreateSuccess){
                    return GetBack.Error()
                }
                sdpObserverEvent.sessionDescription ?: return GetBack.Error()
                sdpObserverEvent.sessionDescription
            }

        signallingUseCase.sendSignallingEvent(SignallingRequests.SendAnswer(answer))

        val mediaStream = rtcMediaCaptureClient.startCapturing()
        mediaStream.audioTracks.forEach {
            rtcConnectionRepository.addTrack(it)
        }
        mediaStream.videoTracks.forEach {
            rtcConnectionRepository.addTrack(it)
        }

        return GetBack.Success()
    }
}