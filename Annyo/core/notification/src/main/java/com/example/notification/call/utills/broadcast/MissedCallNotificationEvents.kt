package com.example.notification.call.utills.broadcast

sealed interface MissedCallNotificationEvents {
    data object OnCallBack: MissedCallNotificationEvents
    data object OnMessage: MissedCallNotificationEvents
}