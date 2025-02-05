package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.model.SignallingRequests
import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.use_cases.signalling_use_case.SignallingUseCases
import com.example.call_domain.util.OfferType
import com.example.call_domain.util.RtcMediaCaptureClient
import com.example.call_domain.util.SdpObserverEvent
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class SendOutgoingCallOfferUseCase @Inject constructor(
    private val rtcConnectionRepository: RtcConnectionRepository,
    private val rtcMediaCaptureClient: RtcMediaCaptureClient,
    private val signallingUseCase: SignallingUseCases,
) {
    suspend operator fun invoke(
        offerType: OfferType,
    ):GetBackBasic {

//       val mediaStream = rtcMediaCaptureClient.startCapturing()  // Fixme uncomment it
//        mediaStream.audioTracks.forEach {
//            rtcConnectionRepository.addTrack(it)
//        }
//        mediaStream.videoTracks.forEach {
//            rtcConnectionRepository.addTrack(it)
//        }

       val sdpEvent = rtcConnectionRepository.createOffer(
            offerType = offerType
        )
        if (sdpEvent !is SdpObserverEvent.OnCreateSuccess) return GetBack.Error()
        sdpEvent.sessionDescription?: return GetBack.Error()

        rtcConnectionRepository.setLocalDescription(
            sdpEvent.sessionDescription
        ).let { sdpObserverEvent->
            if (sdpObserverEvent !is SdpObserverEvent.OnCreateSuccess)
                return GetBack.Error()
        }

        signallingUseCase.sendSignallingEvent(
            SignallingRequests.SendInitialOffer(sdpEvent.sessionDescription
            ))

        return GetBack.Success()
    }
}
