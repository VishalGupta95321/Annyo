package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.util.PeerConnectionObserverEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreatePeerConnectionUseCase @Inject constructor(
    private val rtcConnectionRepository: RtcConnectionRepository,
){
    operator fun invoke(): Flow<PeerConnectionObserverEvent> {
        return rtcConnectionRepository.createPeerConnection()
    }
}