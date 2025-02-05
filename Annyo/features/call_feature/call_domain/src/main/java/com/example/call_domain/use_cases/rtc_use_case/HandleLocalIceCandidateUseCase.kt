package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.model.SignallingRequests
import com.example.call_domain.repository.RtcConnectionRepository
import com.example.call_domain.use_cases.signalling_use_case.SignallingUseCases
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import org.webrtc.IceCandidate
import javax.inject.Inject

class HandleLocalIceCandidateUseCase @Inject constructor(
    private val rtcConnectionRepository: RtcConnectionRepository,
    private val signallingUseCase: SignallingUseCases
) {
    suspend operator fun invoke(
        iceCandidate: IceCandidate?
    ): GetBackBasic {
        iceCandidate ?: return GetBack.Error()

        rtcConnectionRepository.addIceCandidate(iceCandidate)
        signallingUseCase.sendSignallingEvent(
            SignallingRequests.SendIceCandidate(
                candidate = iceCandidate
            ))
        return GetBack.Success()
    }
}