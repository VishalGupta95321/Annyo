package com.example.call_domain.use_cases.signalling_use_case

import com.example.call_domain.model.SignallingSocketEvents
import com.example.call_domain.repository.RtcSignalingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveSignallingSocketConnectionEventUseCase @Inject constructor(
    private val rtcSignalingRepository: RtcSignalingRepository
) {
    operator fun invoke(): Flow<SignallingSocketEvents> {
        return rtcSignalingRepository.observeSignallingSocketConnectionEvent()
    }
}
