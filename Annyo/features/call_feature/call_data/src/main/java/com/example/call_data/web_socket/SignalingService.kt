package com.example.call_data.web_socket

import com.example.call_data.web_socket.request_and_response.SignallingEventRequests
import com.example.call_data.web_socket.request_and_response.SignallingEventsDto
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

interface SignalingService {
    @Receive
    fun observeWebSocketEvent(): Flow<WebSocket.Event>

    @Receive
    fun observeSignalingEvent(): Flow<SignallingEventsDto>

    @Send
    fun sendSignalingEvent(signalingEvent: SignallingEventRequests)
}
