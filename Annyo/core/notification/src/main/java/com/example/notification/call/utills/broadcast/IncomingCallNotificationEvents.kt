package com.example.notification.call.utills.broadcast

sealed interface IncomingCallNotificationEvents {
    data object OnAnswer: IncomingCallNotificationEvents
    data object OnDecline: IncomingCallNotificationEvents
}