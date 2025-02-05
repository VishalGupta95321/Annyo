package com.example.notification.call.utills.broadcast

sealed interface OngoingCallNotificationEvents {
    data object OnHangup: OngoingCallNotificationEvents
}