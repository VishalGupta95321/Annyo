package com.example.notification.call

import android.app.Notification
import com.example.notification.call.model.Individual
import com.example.notification.call.utills.OngoingCallNotificationState

interface CallNotifier {
    fun createIncomingCallNotification(
        caller: Individual,
        isVideoCall: Boolean = false,
    ):Notification

    fun createOngoingCallNotification(
        callee: Individual,
        isVideoCall: Boolean = false,
        state: OngoingCallNotificationState,
    ):Notification

    fun postMissedCallNotification(
        caller: Individual,
        missedCallCount: Int = 1,
        isVideoCall: Boolean = false
    )   /// return notification with result like error and success etc.
}