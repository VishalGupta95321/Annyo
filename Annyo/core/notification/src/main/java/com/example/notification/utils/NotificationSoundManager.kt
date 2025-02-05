package com.example.notification.utils

import android.media.AudioAttributes
import android.net.Uri

interface NotificationSoundManager {
    fun getDefaultDeviceCallRingtone(): NotificationSound
    fun getDefaultDeviceNotification(): NotificationSound
}

data class NotificationSound(
    val uri: Uri,
    val audioAttributes: AudioAttributes,
    val vibrationPattern: List<Long> = listOf()
)