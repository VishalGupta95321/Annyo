package com.example.call_data.web_socket.mapper

import com.example.call_domain.model.SignallingSocketEvents
import com.example.call_domain.util.SocketCloseReasonTypes
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

//import com.example.call_data.web_socket.request_and_response.SocketCloseReason

fun Flow<WebSocket.Event>.mapToSignallingSocketEvent(json: Json):Flow<SignallingSocketEvents> {
     return this.map { webSocketEvent ->
        when(webSocketEvent){
            is WebSocket.Event.OnConnectionOpened<*> -> SignallingSocketEvents.OnConnectionOpened
            is WebSocket.Event.OnMessageReceived -> SignallingSocketEvents.OnMessageReceived
            is WebSocket.Event.OnConnectionClosing -> SignallingSocketEvents.OnConnectionClosing
            is WebSocket.Event.OnConnectionClosed -> {
                try {
                    val obj = json.decodeFromString<SocketCloseReasons>(webSocketEvent.shutdownReason.reason)
                    when(obj){
                        SocketCloseReasons.CallFailed -> {
                            SignallingSocketEvents.OnConnectionClosed(SocketCloseReasonTypes.CallFailed)
                        }
                        SocketCloseReasons.NoSuchCallSession -> {
                            SignallingSocketEvents.OnConnectionClosed(SocketCloseReasonTypes.NoSuchCallSession)
                        }
                        SocketCloseReasons.NoSuchClientId -> {
                            SignallingSocketEvents.OnConnectionClosed(SocketCloseReasonTypes.NoSuchClientId)
                        }

                        SocketCloseReasons.CallSessionMismatch -> {
                            SignallingSocketEvents.OnConnectionClosed(SocketCloseReasonTypes.CallSessionMismatch)
                        }
                    }
                } catch (e:Exception){
                    e.printStackTrace()
                    SignallingSocketEvents.OnConnectionClosed(SocketCloseReasonTypes.UnknownReason)
                }
            }
            is WebSocket.Event.OnConnectionFailed -> SignallingSocketEvents.OnConnectionFailed(
                webSocketEvent.throwable
            )
        }
    }
}

@Serializable
sealed interface SocketCloseReasons{
    data object CallFailed : SocketCloseReasons
    data object NoSuchCallSession : SocketCloseReasons
    data object NoSuchClientId : SocketCloseReasons
    data object CallSessionMismatch: SocketCloseReasons
}
