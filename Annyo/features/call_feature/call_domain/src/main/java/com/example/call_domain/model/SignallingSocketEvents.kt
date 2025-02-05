package com.example.call_domain.model

import com.example.call_domain.util.SocketCloseReasonTypes


sealed interface SignallingSocketEvents {
    data object OnConnectionOpened: SignallingSocketEvents
    data object OnConnectionClosing: SignallingSocketEvents
    data object OnMessageReceived: SignallingSocketEvents
    data class OnConnectionFailed(
        val throwable: Throwable
    ): SignallingSocketEvents
    data class OnConnectionClosed(
       val reason: SocketCloseReasonTypes,
    ): SignallingSocketEvents
}