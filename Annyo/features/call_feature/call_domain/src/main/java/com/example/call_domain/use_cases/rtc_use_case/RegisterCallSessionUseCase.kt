package com.example.call_domain.use_cases.rtc_use_case

import com.example.call_domain.model.SignallingRequests
import com.example.call_domain.use_cases.signalling_use_case.SignallingUseCases
import java.util.UUID
import javax.inject.Inject

class RegisterCallSessionUseCase @Inject constructor(
    private val signallingUseCase: SignallingUseCases,
) {
    suspend operator fun invoke(
        callerId: String,  /// todo we can take caller id from the uri parameter though, think about later
        calleeId: String
    ) {
        signallingUseCase.sendSignallingEvent(
            SignallingRequests.RegisterCallSession(
                sessionId = UUID.randomUUID().toString(), /// todo you may run this uuid generation code in default dispatcher cuz its do calculation under the hood.
                calleeId = callerId,
                callerId = calleeId
            )
        )
    }
}