package com.example.notification

import com.example.notification.utils.NotificationChannelIds

interface Notifier {
    fun postNotification(
        channelId: NotificationChannelIds
    )
}