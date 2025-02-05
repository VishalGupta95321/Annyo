package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.repository.RtcConnectionRepository
import org.webrtc.IceCandidate
import javax.inject.Inject

class HandleRemoteIceCandidateUseCase @Inject constructor(
    private val rtcConnectionRepository: RtcConnectionRepository
) {
    suspend operator fun invoke(
        remoteIceCandidate:IceCandidate
    ) {
        rtcConnectionRepository.addIceCandidate(remoteIceCandidate)
    }
}