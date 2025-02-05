package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.util.SdpObserverEvent
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import org.webrtc.SessionDescription
import javax.inject.Inject

class HandlePeersAnswerUseCase @Inject constructor(
    private val rtcConnectionRepository: RtcConnectionRepository,
) {
    suspend operator fun invoke(sessionDescription: SessionDescription): GetBackBasic {
        val sdpEvent = rtcConnectionRepository.setRemoteDescription(sessionDescription)
        if (sdpEvent !is SdpObserverEvent.OnCreateSuccess) return GetBack.Error()
        return GetBack.Success()
    }
}  /// todo if required