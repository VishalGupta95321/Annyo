package com.example.notification.utils

import android.content.Context
import androidx.core.app.NotificationCompat


fun Context.createBaseNotification(
    channelId: NotificationChannelIds,
    sound: NotificationSound,
    category: String? = null,
    visibility: Int = NotificationCompat.VISIBILITY_PUBLIC,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT,
): NotificationCompat.Builder {

    return NotificationCompat.Builder(
        this,
        channelId.id
    ).setPriority(priority)
        .setCategory(category)
        .setVisibility(visibility)
        .setVibrate(sound.vibrationPattern.toLongArray())
        .setSound(sound.uri)
}