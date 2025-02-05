package com.example.call_domain.repository

import com.app.annyo.models.request.SignallingAcknowledgmentRequests
import com.example.call_domain.model.SignallingEvents
import com.example.call_domain.model.SignallingRequests
import com.example.call_domain.model.SignallingSocketEvents
import kotlinx.coroutines.flow.Flow

interface RtcSignalingRepository {
    fun observeSignalingEvent():Flow<SignallingEvents>
    fun observeSignallingSocketConnectionEvent():Flow<SignallingSocketEvents>
    suspend fun sendSignallingEvent(
        signalingEventRequest: SignallingRequests
    )
    suspend fun sendSignallingAcknowledgement(
        acknowledgementRequest: SignallingAcknowledgmentRequests
    )
}