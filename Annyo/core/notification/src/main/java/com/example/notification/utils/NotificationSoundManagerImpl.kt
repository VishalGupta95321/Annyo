package com.example.notification.utils

import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import javax.inject.Inject

class NotificationSoundManagerImpl @Inject constructor(

): NotificationSoundManager {
    override fun getDefaultDeviceCallRingtone(): NotificationSound {

        val vibrationPattern = listOf(100L, 50000L)

        val uri = RingtoneManager
            .getDefaultUri(RingtoneManager.TYPE_RINGTONE)

        val attribute = AudioAttributes.Builder().apply {
            setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            //setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION) // To play sound in earpiece
            setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
        }.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                it.setHapticChannelsMuted(false)
                    .build()
            } else it.build()
        }
        return NotificationSound(uri,attribute,vibrationPattern)
    }

    override fun getDefaultDeviceNotification(): NotificationSound {  ///todo edit this and clean the duplicate code
        val vibrationPattern = listOf(100L, 50000L)

        val uri = RingtoneManager
            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val attribute = AudioAttributes.Builder().apply {
            setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            //setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION) // To play sound in earpiece
            setUsage(AudioAttributes.USAGE_NOTIFICATION)
            setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
        }.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                it.setHapticChannelsMuted(false)
                    .build()
            } else it.build()
        }
        return NotificationSound(uri,attribute,vibrationPattern)
    }
}