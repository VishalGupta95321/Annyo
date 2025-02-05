package com.example.call_data.repository

import com.annyo.network.di.IoDispatcher
import com.app.annyo.models.request.SignallingAcknowledgmentRequests
import com.example.call_data.web_socket.SignalingService
import com.example.call_data.web_socket.mapper.mapToSignallingSocketEvent
import com.example.call_data.web_socket.request_and_response.mapToSignallingEvents
import com.example.call_data.web_socket.request_and_response.toSignallingAcknowledgeRequests
import com.example.call_data.web_socket.request_and_response.toSignallingEventRequests
import com.example.call_domain.model.SignallingEvents
import com.example.call_domain.model.SignallingRequests
import com.example.call_domain.model.SignallingSocketEvents
import com.example.call_domain.repository.RtcSignalingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class RtcSignalingRepositoryImpl @Inject constructor(
    private val signalingService : SignalingService,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val json: Json
): RtcSignalingRepository {

    override fun observeSignalingEvent(): Flow<SignallingEvents> =
        signalingService.observeSignalingEvent().mapToSignallingEvents().flowOn(ioDispatcher)

    override fun observeSignallingSocketConnectionEvent(): Flow<SignallingSocketEvents> =
        signalingService.observeWebSocketEvent().mapToSignallingSocketEvent(json).flowOn(ioDispatcher)

    override suspend fun sendSignallingEvent(signalingEventRequest: SignallingRequests) {
        withContext(ioDispatcher) {
            signalingService.sendSignalingEvent(signalingEventRequest.toSignallingEventRequests())
        }
    }

    override suspend fun sendSignallingAcknowledgement(acknowledgementRequest: SignallingAcknowledgmentRequests) {
        withContext(ioDispatcher) {
            signalingService.sendSignalingEvent(acknowledgementRequest.toSignallingAcknowledgeRequests())
        }
    }
}


//override suspend fun sendIceCandidate(iceCandidate: SignalingEvent.OnIceCandidate){
//    signalingService.sendSignalingEvent(iceCandidate).also {
//        return if (it) GetBack.Success() else GetBack.Error()
//    }
//}    /// sendSignalingEvent returns nothing