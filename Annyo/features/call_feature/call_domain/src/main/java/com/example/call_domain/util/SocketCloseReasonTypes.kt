package com.example.call_domain.util

sealed interface SocketCloseReasonTypes{
    data object CallFailed : SocketCloseReasonTypes
    data object NoSuchCallSession : SocketCloseReasonTypes
    data object NoSuchClientId : SocketCloseReasonTypes
    data object UnknownReason: SocketCloseReasonTypes
    data object CallSessionMismatch: SocketCloseReasonTypes
}