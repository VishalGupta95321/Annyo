package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.model.SignallingRequests
import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.use_cases.signalling_use_case.SignallingUseCases
import com.example.call_domain.util.RtcMediaCaptureClient
import javax.inject.Inject

class HangUpOutgoingCallUseCase @Inject constructor(
    private val rtcConnectionRepository: RtcConnectionRepository,
    private val rtcMediaCaptureClient: RtcMediaCaptureClient,
    private val signallingUseCase: SignallingUseCases,
) {
    suspend operator fun invoke() {
        rtcConnectionRepository.closePeerConnection()
        signallingUseCase.sendSignallingEvent(SignallingRequests.SendHangUp)
        rtcMediaCaptureClient.stopCapturing() /// todo( currently ignoring the exception)
    }
}