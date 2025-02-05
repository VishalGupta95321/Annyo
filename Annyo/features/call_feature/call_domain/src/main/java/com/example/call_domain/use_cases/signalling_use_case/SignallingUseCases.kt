package com.example.call_domain.use_cases.signalling_use_case

data class SignallingUseCases(
    val observeSignallingSocketConnectionEvent: ObserveSignallingSocketConnectionEventUseCase,
    val observeSignallingEvent: ObserveSignallingEventUseCase,
    val sendSignallingEvent: SendSignallingEventUseCase,
    val sendSignallingAcknowledgmentsUseCase: SendSignallingAcknowledgmentsUseCase
)
