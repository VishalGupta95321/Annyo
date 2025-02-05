package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.util.RtcMediaCaptureClient
import javax.inject.Inject

class HandleIncomingCallHangUpRequestUseCase @Inject constructor(
    private val rtcConnectionRepository: RtcConnectionRepository,
    private val rtcMediaCaptureClient: RtcMediaCaptureClient,
) {
    suspend operator fun invoke() {
        rtcConnectionRepository.closePeerConnection()
        rtcMediaCaptureClient.stopCapturing()
    }
}  /// todo maybe change the use case name