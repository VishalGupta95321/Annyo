package com.example.notification.call.utills

sealed interface OngoingCallNotificationState{
    data object CallConnecting: OngoingCallNotificationState
    data object CallRinging: OngoingCallNotificationState
    data object CallBusy: OngoingCallNotificationState
    data object CallReconnecting: OngoingCallNotificationState
    data object CallOngoing: OngoingCallNotificationState
}