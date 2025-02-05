package com.app.annyo.exception

import kotlinx.serialization.Serializable

@Serializable
sealed interface SocketCloseReasons{
    data object CallFailed : SocketCloseReasons
    data object NoSuchCallSession : SocketCloseReasons
    data object NoSuchClientId : SocketCloseReasons
    data object CallSessionMismatch: SocketCloseReasons
}
