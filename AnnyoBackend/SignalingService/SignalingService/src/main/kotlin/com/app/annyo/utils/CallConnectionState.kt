package com.app.annyo.utils

sealed interface CallConnectionState {
    data object Connected: CallConnectionState
    data object NotConnected: CallConnectionState
}