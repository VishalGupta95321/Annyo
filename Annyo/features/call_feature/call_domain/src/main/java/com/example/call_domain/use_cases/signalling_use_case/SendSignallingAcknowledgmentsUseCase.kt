package com.example.call_domain.use_cases.signalling_use_case

import com.app.annyo.models.request.SignallingAcknowledgmentRequests
import com.example.call_domain.repository.RtcSignalingRepository
import javax.inject.Inject

class SendSignallingAcknowledgmentsUseCase @Inject constructor(
    private val rtcSignalingRepository: RtcSignalingRepository
) {
    suspend operator fun invoke(acknowledgement: SignallingAcknowledgmentRequests ) {
        rtcSignalingRepository.sendSignallingAcknowledgement(acknowledgement)
    }
}