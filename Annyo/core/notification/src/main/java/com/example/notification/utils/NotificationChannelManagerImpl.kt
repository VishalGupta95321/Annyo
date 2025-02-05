package com.example.notification.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.annyo.core.notification.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class NotificationChannelManagerImpl @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val notificationManagerCompat: NotificationManagerCompat,
    private val soundManager: NotificationSoundManager
):NotificationChannelManager{

    override fun createCallNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val sound = soundManager.getDefaultDeviceCallRingtone()

        createNotificationChannel(
            sound,
            context.getString(R.string.annyo_call_notification_channel_name),
            NotificationChannelIds.CallChannel.id,
            NotificationManager.IMPORTANCE_HIGH
        )
    }

    override fun createUnCategorizedNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val sound = soundManager.getDefaultDeviceNotification()

        createNotificationChannel(
            sound,
            context.getString(R.string.annyo_uncategorized_channel_name),
            NotificationChannelIds.UncategorizedChannel.id,
        )
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(
        sound: NotificationSound,
        channelName: String,
        channelId: String,
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ):NotificationChannel{

        return NotificationChannel(
            channelId,
            channelName,
            importance
        ).apply {
            enableVibration(sound.vibrationPattern.isNotEmpty())
            vibrationPattern  = sound.vibrationPattern.toLongArray()/// customize more with sound etc //
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            setSound(sound.uri, sound.audioAttributes)
        }.also {
            notificationManagerCompat.createNotificationChannel(it)  /// todo remove compat maybe
        }
    }
}

sealed class NotificationChannelIds( /// todo dont know why chose sealed class instead of object
    val id: String
){
    data object CallChannel: NotificationChannelIds(id = "0")
    data object UncategorizedChannel: NotificationChannelIds(id = "1")
}