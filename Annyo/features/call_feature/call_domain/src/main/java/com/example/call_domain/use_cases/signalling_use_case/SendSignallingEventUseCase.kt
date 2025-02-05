package com.example.call_domain.use_cases.signalling_use_case

import com.example.call_domain.model.SignallingRequests
import com.example.call_domain.repository.RtcSignalingRepository
import javax.inject.Inject

class SendSignallingEventUseCase @Inject constructor(
    private val rtcSignalingRepository: RtcSignalingRepository
) {
    suspend operator fun invoke(signallingEvent:SignallingRequests) {
        rtcSignalingRepository.sendSignallingEvent(signallingEvent)
    }
}
