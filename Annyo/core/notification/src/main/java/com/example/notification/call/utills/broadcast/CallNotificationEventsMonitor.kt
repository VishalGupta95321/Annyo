package com.example.notification.call.utills.broadcast

interface CallNotificationEventsMonitor {
    suspend fun getIncomingCallNotificationEvents():IncomingCallNotificationEvents?
    suspend fun getOngoingCallNotificationEvents():OngoingCallNotificationEvents?
    suspend fun getMissedCallNotificationEvents():MissedCallNotificationEvents?
}
